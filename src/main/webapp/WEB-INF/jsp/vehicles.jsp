<%@ page session="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row">
  <div class="col-sm-6">
    <div class="card">
      <div class="card-header">Maintenance Options</div>
      <div class="card-body">
        <table id="vehicleTable" class="table table-striped table-bordered" style="width:100%">
          <thead>
            <tr>
              <th>VIN</th>
              <th>Make</th>
              <th>Model</th>
              <th>Year</th>
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
      <div class="card-header">Vehicle Details</div>
      <div class="card-body">
        <div class="alert alert-success" id="alertSuccess" role="alert" style="display: none;">
          Update was successful
        </div>
        <div class="alert alert-danger" id="alertError" role="alert" style="display: none;">
        </div>
                
        <form id="optionform" >
          <div class="form-group">
            <label for="oid">oid</label>
            <input id="oid" name="oid" type="text" size="15" class="form-control" readonly="readonly">
          </div>
          <div class="form-group">
            <label for="make">Make</label>
            <input id="make" name="make" type="text" size="15" class="form-control" readonly="readonly">
          </div>
          <div class="form-group">
            <label for="model">Model</label>
            <input id="model" name="model" type="text" size="15" class="form-control" readonly="readonly">
          </div>
          <div class="form-group">
            <label for="year">Year</label>
            <input id="year" name="year" type="text" size="5" class="form-control" readonly="readonly">
          </div>
          <div class="form-group">
            <label for="vin">VIN</label>
            <input id="vin" name="vin" type="text" size="20" class="form-control" readonly="readonly">
          </div>
          <div class="form-group">
            <label for="engineType">Engine Type</label>
            <input id="engineType" name="engineType" type="text" size="20" class="form-control" readonly="readonly">
          </div>
          <div class="form-group">
            <label for="odometerReading">Odometer Reading</label>
            <input id="odometerReading" name="odometerReading" type="text" size="20" class="form-control">
          </div>
          
          <span class="float-right pt-1">
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
        <h5 class="modal-title" id="moModalCenterTitle">New Vehicle</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <div class="alert alert-danger" id="alertErrorCreate" role="alert" style="display: none;">
        </div>
      
        <form id="vehicleCreateForm" >
          <div class="form-group">
            <label for="createMake">Make</label>
            <input id="createMake" name="make" type="text" size="15" class="form-control">
          </div>
          <div class="form-group">
            <label for="createModel">Model</label>
            <input id="createModel" name="model" type="text" size="15" class="form-control">
          </div>
          <div class="form-group">
            <label for="createYear">Year</label>
            <input id="createYear" name="year" type="text" size="5" class="form-control">
          </div>
          <div class="form-group">
            <label for="createEngineType">Engine Type</label>
            <select id="createEngineType" name="engineType">
            <c:forEach items="${ENGINE_TYPES}" var="type">
              <option value="${type.code}">${type.description}</option>
            </c:forEach>
            </select>
          </div>
          <div class="form-group">
            <label for="createVin">VIN</label>
            <input id="createVin" name="vin" type="text" size="20" class="form-control">
          </div>
          <div class="form-group">
            <label for="createOdometerReading">Odometer Reading</label>
            <input id="createOdometerReading" name="odometerReading" type="text" size="20" class="form-control">
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

<script src="/js/datafunctions.js"></script>
<script type="text/javascript">
var map = new Object();

var table = $('#vehicleTable').DataTable({
  "searching": true,
  "order": [[ 1, "desc" ]],
  "select": { "style": "single" },
  "rowId": 'oid',
  "ajax": { "url": "app/api/vehicle" },
  "columns" : [
      {"data": "vin"},
      {"data": "make"},
      {"data": "model"},
      {"data": "year"}
  ]
});

function deleteEntity() {
  $.ajax({
    url: 'app/api/vehicle/' + $("#oid").val(),
    type: "DELETE",
    dataType: "json",
    success : function(data, textStatus, jqXHR) {
        table.ajax.reload();
        $("#alertSuccess").show().delay(2000).fadeOut();
      },
      error : function(jqXHR, textStatus, errorThrown) {
        $('#alertError').html(jqXHR.responseText + " -- " + textStatus + " -- " + errorThrown);
        $('#alertError').show();
      }          
  });
}

function create() {
  var data = JSON.stringify(convertFormToJSON($("#vehicleCreateForm")));
  
  console.log("Create JSON: " + data)
  $.ajax({
    url: 'app/api/vehicle',
    type: "PUT",
    data: data,
    contentType: "application/json",
    success : function(data, textStatus, jqXHR) {
        var loc = jqXHR.getResponseHeader('Location').split("/");
        var uuid = loc[loc.length-1];
    	console.log("UUID: " + uuid);
    	
        table.ajax.reload(function (json) {
          table.row(uuid).select();
          $('#createMake').val("");
          $("#createModel").val("");
          $("#createYear").val("");
          $("#createVin").val("");
          $("#createEngineType").val([]);
          $("#createOdometerReading").val("");
          
          $('#createNewModal').modal('hide');
        });

        $("#alertSuccess").show().delay(2000).fadeOut();
      },
      error : function(jqXHR, textStatus, errorThrown) {
        $('#alertErrorCreate').html(jqXHR.responseText + " -- " + textStatus + " -- " + errorThrown);
        $('#alertErrorCreate').show();
      }          
  });
}

function patch() {
  $('#butSubmit').disable(true);
  $('#alertError').hide();

  var data = [];
  for (var k in map) {
    data.push({ "op": "replace", "path" : k, "value" : map[k] });
  }
  
  $.ajax({
    url : 'app/api/vehicle/' + $("#oid").val(),
    data : JSON.stringify(data),
    contentType : "application/json",
    type : 'PATCH',
    dataType: "json",
    success : function(data, textStatus, jqXHR) {
      table.ajax.reload(null, false);
      map = new Object();
      table.row($("#oid").val()).select();
      $("#alertSuccess").show().delay(2000).fadeOut();
    },
    error : function(jqXHR, textStatus, errorThrown) {
      $('#alertError').html(jqXHR.responseText + " -- " + textStatus + " -- " + errorThrown);
      $('#alertError').show();
    }          
  });
}

$(document).ready(function() {
  table.on( 'select', function ( e, dt, type, indexes ) {
    $('#alertError').hide();
    var rowData = table.rows( indexes ).data().toArray();
    
    $.ajax({
      type: "GET",
      url: "app/api/vehicle/" + rowData[0].oid,
      dataType: "json",
      headers : {  "Content-Type" : "application/json" },
      success: function(data, textStatus, jqXHR) {
        $("#oid").val(data["oid"]);
        $("#make").val(data["make"]);
        $("#model").val(data["model"]);
        $("#year").val(data["year"]);
        $("#engineType").val(data["engineType"]);
        $("#vin").val(data["vin"]);
        $("#odometerReading").val(data["odometerReading"]);
        
        bindField('odometerReading');
        $('#butDelete').disable(false);
        $('#butSubmit').disable(true);
      },
    });        
  })
  .on( 'deselect', function ( e, dt, type, indexes ) {
      $('#alertError').hide();
      $("#oid").val("");
      $("#make").val("");
      $("#model").val("");
      $("#year").val("");
      $("#engineType").val("");
      $("#vin").val("");
      $("#odometerReading").val("");
      unbindField('odometerReading');
      $('#butSubmit').disable(true);
      $('#butDelete').disable(true);
  });
});
</script>