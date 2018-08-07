<%@ page session="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row">
  <div class="col-sm-6">
    <div class="card">
      <div class="card-header">Maintenance Options</div>
      <div class="card-body">
        <table id="optionTable" class="table table-striped table-bordered" style="width:100%">
          <thead>
            <tr>
              <th>Code</th>
              <th>Description</th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
      </div> 
    </div>
    <div class="card-footer">
      <span class="float-right pt-1">
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createNewModal">
          Create New
        </button>          
      </span>
    </div>  
  </div>

  <div class="col-sm-6">
    <div class="card">
      <div class="card-header">Maintenance Options</div>
      <div class="card-body">
        <div class="alert alert-success" id="alertSuccess" role="alert" style="display: none;">
          Update was successful
        </div>
        <div class="alert alert-danger" id="alertError" role="alert" style="display: none;"></div>
                
        <form id="optionform" >
          <div class="form-group row">
            <label for="code" class="col-sm-3 col-form-label">Code</label>
            <div class="col-sm-7">
              <input id="code" name="code" type="text" size="15" class="form-control" readonly="readonly">
            </div>
          </div>
          <div class="form-group row">
            <label for="description" class="col-sm-3 col-form-label">Description</label>
            <div class="col-sm-7">
              <input id="description" name="description" type="text" size="256" class="form-control">
            </div>
          </div>
          
          <span class="float-right pt-1">
            <button type="button" id="butUpdate" class="btn btn-success" disabled>Submit Changes</button>
            <button type="button" id="butDelete" class="btn btn-danger" disabled>Delete</button>
          </span>
        </form>  
      </div> 
    </div>    
  </div>
</div>

<div class="modal fade" id="createNewModal" tabindex="-1" role="dialog" aria-labelledby="moModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="moModalCenterTitle">New Maintenance Options</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <div class="alert alert-danger" id="alertErrorCreate" role="alert" style="display: none;">
        </div>
        <form id="moCreateForm" >
          <div class="form-group row">
            <label for="createCode" class="col-sm-3 col-form-label">Code</label>
            <div class="col-sm-7">
              <input id="createCode" name="code" type="text" size="15" class="form-control">
            </div>
          </div>
          <div class="form-group row">
            <label for="createDescription" class="col-sm-3 col-form-label">Description</label>
            <div class="col-sm-7">
              <input id="createDescription" name="description" type="text" size="256" class="form-control">
            </div>
          </div>
        </form>  
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" id="butCreate" >Create</button>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="modalDelete">
  <div class="modal-dialog modal-sm-3 modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="moModalCenterTitle">Confirm Deletion</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" id="butConfirmDelete">Yes</button>
        <button type="button" class="btn btn-primary" id="butCancelDelete">No</button>
      </div>
    </div>
  </div>
</div>

<script src="/js/datafunctions.js"></script>
<script type="text/javascript">
var map = new Object();

var table = $('#optionTable').DataTable({
  "searching": true,
  "order": [[ 1, "desc" ]],
  "select": { "style": "single" },
  "rowId": 'code',
  "ajax": { "url": "api/settings/maintenanceoptions" },
  "columns" : [
      {"data": "code"},
      {"data": "description"},
  ]
});

var modalConfirm = function(callback){
  $("#butDelete").on("click", function() {
    $("#modalDelete").modal('show');
  });

  $("#butConfirmDelete").on("click", function() {
    callback(true);
    $("#modalDelete").modal('hide');
  });
  
  $("#butCancelDelete").on("click", function() {
    callback(false);
    $("#modalDelete").modal('hide');
  });
};

modalConfirm(function(confirm){
  if(confirm){
    $.ajax({
      url: 'api/settings/maintenanceoptions/' + $("#code").val(),
      type: "DELETE",
      success : function(response, textStatus, jqXhr) {
          table.ajax.reload();
          $("#alertSuccess").show().delay(2000).fadeOut();
          $('#code').val("");
          $("#description").val("");
          unbindField('description');
          $('#butUpdate').disable(true);
          $('#butDelete').disable(true);
        },
        error : function(jqXHR, textStatus, errorThrown) {
          $('#alertError').html(jqXHR.responseText + " -- " + textStatus + " -- " + errorThrown);
          $('#alertError').show();
        }          
    });
  }
});

$("#butCreate").on("click", function(){
  $.ajax({
    url: 'api/settings/maintenanceoptions',
    type: "PUT",
    data: JSON.stringify(convertFormToJSON($("#moCreateForm"))),
    contentType: "application/json",
    success : function(response, textStatus, jqXhr) {
        table.ajax.reload(function (json) {
          var newCode = '#' + $("#createCode").val();
          table.row(newCode).select();
          $('#createCode').val("");
          $("#createDescription").val("");
          $('#createNewModal').modal('hide');
        });

        $("#alertSuccess").show().delay(2000).fadeOut();
      },
      error : function(jqXHR, textStatus, errorThrown) {
        $('#alertErrorCreate').html(jqXHR.responseText + " -- " + textStatus + " -- " + errorThrown);
        $('#alertErrorCreate').show();
      }          
  });
});

$("#butUpdate").on("click", function() {
  $('#butUpdate').disable(true);
  $('#alertError').hide();

  var data = [];
  for (var k in map) {
    data.push({ "op": "replace", "path" : k, "value" : map[k] });
  }
  
  $.ajax({
    url : 'api/settings/maintenanceoptions/' + $("#code").val(),
    data : JSON.stringify(data),
    contentType : "application/json",
    type : 'PATCH',
    success : function(response, textStatus, jqXhr) {
      table.ajax.reload(null, false);
      map = new Object();
      table.row($("#code").val()).select();
      $("#alertSuccess").show().delay(2000).fadeOut();
    },
    error : function(jqXHR, textStatus, errorThrown) {
      $('#alertError').html(jqXHR.responseText + " -- " + textStatus + " -- " + errorThrown);
      $('#alertError').show();
    }          
  });
});

table.on( 'select', function ( e, dt, type, indexes ) {
  $('#alertError').hide();
  var rowData = table.rows( indexes ).data().toArray();
  
  $.ajax({
    type: "GET",
    url: "api/settings/maintenanceoptions/" + rowData[0].code,
    dataType: "json",
    headers : {  "Content-Type" : "application/json" },
    success: function(data) {
      $("#code").val(data["code"]);
      $("#description").val(data["description"]);
      bindField('description');
      $('#butDelete').disable(false);
      $('#butSubmit').disable(true);
    },
  });        
})
.on( 'deselect', function ( e, dt, type, indexes ) {
    $('#alertError').hide();
    $('#code').val("");
    $("#description").val("");
    unbindField('description');
    $('#butUpdate').disable(true);
    $('#butDelete').disable(true);
});
</script>