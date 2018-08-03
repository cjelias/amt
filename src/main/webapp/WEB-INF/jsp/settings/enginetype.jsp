<%@ page session="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row">
  <div class="col-sm-6">
    <div class="card">
      <div class="card-header">Engine Types</div>
      <div class="card-body">
        <table id="optionlist" class="table table-striped table-bordered" style="width:100%">
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
      <div class="card-header">Engine Type</div>
      <div class="card-body">
        <div class="alert alert-success" id="alertSuccess" role="alert">
          Update was successful
        </div>
        <div class="alert alert-danger" id="alertError" role="alert">
          Update Failed.
        </div>
                
        <form id="optionform" >
          <div class="form-group">
            <label for="code">Code</label>
            <input id="code" name="code" type="text" size="15" class="form-control" readonly="readonly">
          </div>
          <div class="form-group">
            <label for="description">Description</label>
            <input id="description" name="description" type="text" size="256" class="form-control">
          </div>
          
          <span class="float-right pt-1">
            <button type="button" id="butReset" onclick="resetForm()" class="btn btn-danger" disabled>Reset</button>
            <button type="button" id="butSubmit" onclick="patch()" class="btn btn-success" disabled>Submit Changes</button>
            <button type="button" id="butDelete" onclick="deleteEntity()" class="btn btn-danger" disabled>Delete</button>
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
        <h5 class="modal-title" id="moModalCenterTitle">New Engine Type</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <form id="moCreateForm" >
          <div class="form-group">
            <label for="createCode">Code</label>
            <input id="createCode" name="code" type="text" size="15" class="form-control">
          </div>
          <div class="form-group">
            <label for="createDescription">Description</label>
            <input id="createDescription" name="description" type="text" size="256" class="form-control">
          </div>
        </form>  
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" onclick="create()" >Create</button>
      </div>
    </div>
  </div>
</div>
  
<script type="text/javascript">
var map = new Object();

var table = $('#optionlist').DataTable({
  "searching": true,
  "order": [[ 1, "desc" ]],
  "select": { "style": "single" },
  "rowId": 'code',
  "ajax": { "url": "app/api/settings/enginetype" },
  "columns" : [
      {"data": "code"},
      {"data": "description"},
  ]
});

function resetForm() {
  $('#butSubmit').disable(true);
  $('#butReset').disable(true);
  $("#optionform")[0].reset();
}

function bindField(field) {
  var fieldName = '#'+field; 
  console.log("binding " + fieldName);
  $(fieldName).bind("change paste keyup", function() {
    $('#butSubmit').disable(false);
    $('#butReset').disable(false);
    map[field] = $(this).val();
  });
}

function unbindField(field) {
  var fieldName = '#'+field; 
  $(fieldName).unbind("change paste keyup");
}

function deleteEntity() {
  $.ajax({
    url: 'app/api/settings/enginetype/' + $("#code").val(),
    type: "DELETE",
    success : function(response, textStatus, jqXhr) {
        table.ajax.reload();
        $("#alertSuccess").show().delay(2000).fadeOut();
      },
      error : function(jqXHR, textStatus, errorThrown) {
        $('#alertError').show();
      }          
  });
}

function create() {
  var data = JSON.stringify(convertFormToJSON("#moCreateForm"));
  
  $.ajax({
    url: 'app/api/settings/enginetype',
    type: "PUT",
    data: data,
    contentType: "application/json",
    success : function(response, textStatus, jqXhr) {
        table.ajax.reload(function (json) {
          var newCode = '#' + $("#createCode").val();
          table.row(newCode).select();
        });

        $("#alertSuccess").show().delay(2000).fadeOut();
      },
      error : function(jqXHR, textStatus, errorThrown) {
        $('#alertError').show();
      }          
  });
}

function patch() {
  $('#butSubmit').disable(true);
  $('#butReset').disable(true);
  $('#alertError').hide();

  var data = [];
  for (var k in map) {
    data.push({ "op": "replace", "path" : k, "value" : map[k] });
  }
  
  var patchData = JSON.stringify(data);
  
  $.ajax({
    url : 'app/api/settings/enginetype/' + $("#code").val(),
    data : patchData,
    contentType : "application/json",
    type : 'PATCH',
    success : function(response, textStatus, jqXhr) {
      table.ajax.reload(null, false);
      map = new Object();
      table.row($("#code").val()).select();
      $("#alertSuccess").show().delay(2000).fadeOut();
    },
    error : function(jqXHR, textStatus, errorThrown) {
      $('#alertError').show();
    }          
  });
}

$(document).ready(function() {
  $('#alertSuccess').hide();
  $('#alertError').hide();
    
  $('#butSubmit').disable(true);
  
  table.on( 'select', function ( e, dt, type, indexes ) {
    var rowData = table.rows( indexes ).data().toArray();
    
    $.ajax({
      type: "GET",
      url: "app/api/settings/enginetype/" + rowData[0].code,
      dataType: "json",
      headers : {  "Content-Type" : "application/json" },
      success: function(data) {
        $("#code").val(data["code"]);
        $("#description").val(data["description"]);
        bindField('description');
        $('#butDelete').disable(false);
      },
      error : function(jqXHR, textStatus, errorThrown) {
        console.log("The following error occured: " + textStatus, errorThrown);
      },
      complete : function() {
        console.log("Maintenance Options GET Ran");
      }          
    });        
  })
  .on( 'deselect', function ( e, dt, type, indexes ) {
      $('#code').val("");
      $("#description").val("");
      unbindField('description');
      selectedRow = null;
      $('#butDelete').disable(true);
  });
});
</script>