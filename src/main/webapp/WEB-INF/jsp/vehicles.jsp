<%@ page session="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:template>

<jsp:attribute name="extraJavascript">
<script type="text/javascript">
var map = new Object();

var table = $('#vehicleTable').DataTable({
  "lengthChange": false,
  "searching": true,
  "order": [[ 1, "desc" ]],
  "select": { "style": "single" },
  "rowId": 'oid',
  "ajax": { "url": "api/vehicle" },
  "columns" : [
      {"data": "vin"},
      {"data": "make"},
      {"data": "model"},
      {"data": "year"},
      {"data": "engineType"},
      {"data": "odometerReading"}
  ]
});

var historyTable = $('#historyTable').DataTable({
  "lengthChange": false,
  "searching": false,
  "order": [[ 0, "desc" ]],
  "select": { "style": "single" },
  "columns" : [ {"data": "date"}, {"data": "options"} ]
});

var modalConfirm = function(callback){
  $("#butDelete").on("click", function(){
    $("#modalDelete").modal('show');
  });

  $("#butConfirmDelete").on("click", function(){
    callback(true);
    $("#modalDelete").modal('hide');
  });
  
  $("#butCancelDelete").on("click", function(){
    callback(false);
    $("#modalDelete").modal('hide');
  });
};

modalConfirm(function(confirm){
  if(confirm){
    $.ajax({
      url: 'api/vehicle/' + $("#oid").val(),
      type: "DELETE",
      success : function(data, textStatus, jqXHR) {
          table.ajax.reload();
          $("#alertSuccess").show().delay(2000).fadeOut();
          $('#code').val("");
          $("#description").val("");
          $('.form-check-input').prop('checked', false);
          $(".form-check-input").attr("disabled", true);
          unbindField('description');
          selectedRow = null;
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

var modalAddAppointment = function(callback){
  $("#butAddAppoint").on("click", function(){
    $("#modalAddAppointment").modal('show');
  });

  $("#butCreateAppointment").on("click", function(){
    callback(true);
    $("#modalAddAppointment").modal('hide');
  });
  
  $("#butCancelAppointment").on("click", function(){
    callback(false);
    $("#modalAddAppointment").modal('hide');
  });
};


$("#butCancel").on("click", function() {
  resetForm();
});

modalAddAppointment(function(confirm){
  if(confirm) {
    $.ajax({
      url: 'api/vehicle/' + $("#oid").val() +'/appointment' ,
      type: "PUT",
      data:  JSON.stringify(convertFormToJSON($("#addAppointmentForm"))),
      contentType: "application/json",
      success : function(data, textStatus, jqXHR) {
          $("#alertSuccess").show().delay(2000).fadeOut();
          historyTable.ajax.url("api/vehicle/" + $("#oid").val() + "/appointment");
          historyTable.ajax.reload();
        },
        error : function(jqXHR, textStatus, errorThrown) {
          var responseObj = JSON.parse(jqXHR.responseText);
          $('#alertErrorCreate').html(responseObj.message);
          $('#alertErrorCreate').show();
        }          
    });
  }
});

$("#butCreate").on("click", function() {
  $.ajax({
    url: 'api/vehicle',
    type: "PUT",
    data:  JSON.stringify(convertFormToJSON($("#vehicleCreateForm"))),
    contentType: "application/json",
    success : function(data, textStatus, jqXHR) {
        var loc = jqXHR.getResponseHeader('Location').split("/");
        var uuid = loc[loc.length-1];
        table.ajax.reload(function (json) {
          table.row(uuid).select();
          resetForm();          
        });

        $("#alertSuccess").show().delay(2000).fadeOut();
      },
      error : function(jqXHR, textStatus, errorThrown) {
        var responseObj = JSON.parse(jqXHR.responseText);
        $('#alertErrorCreate').html(responseObj.message);
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
    url : 'api/vehicle/' + $("#oid").val(),
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
});

$(document).ready(function() {
  table.on( 'select', function ( e, dt, type, indexes ) {
    $('#alertError').hide();
    var rowData = table.rows( indexes ).data().toArray();
    
    $.ajax({
      type: "GET",
      url: "api/vehicle/" + rowData[0].oid,
      dataType: "json",
      headers : {  "Content-Type" : "application/json" },
      success: function(data, textStatus, jqXHR) {
        $("#oid").val(data["oid"]);
        $("#year").val(data["year"]);
        $("#make").val(data["make"]);
        $("#model").val(data["model"]);
        $("#engineType").val(data["engineType"]);
        $("#vin").val(data["vin"]);
        $("#odometerReading").val(data["odometerReading"]);
        bindField('odometerReading');
        
        historyTable.ajax.url("api/vehicle/" + rowData[0].oid + "/appointment");
        historyTable.ajax.reload();
        
        $("#appointmentContainer").load('ui/vehicles/' + rowData[0].oid + '/addappointment/' + $("#engineType").val());
        
        $('#butAddAppoint').disable(false);
        $('#butDelete').disable(false);
        $('#butUpdate').disable(true);
      },
    });        
  })
  .on( 'deselect', function ( e, dt, type, indexes ) {
    resetForm();
  });
});

function resetForm() {
  $('form input, form select').each( function(index) { $(this).val(""); } );
  $('#alertError').hide();
  $('#createNewModal').modal('hide');
  $('#butAddAppoint').disable(true);
  $('#butUpdate').disable(true);
  $('#butDelete').disable(true);
  unbindField('odometerReading');
}
</script>
</jsp:attribute>

<jsp:body>
<div class="row">
  <div class="col-sm-12">
    <div class="card">
      <div class="card-header">Vehicles</div>
      <div class="card-body">
        <table id="vehicleTable" class="table table-striped table-bordered" style="width:100%">
          <thead>
            <tr>
              <th>VIN</th>
              <th>Year</th>
              <th>Make</th>
              <th>Model</th>
              <th>Egine Type</th>
              <th>Odometer Reading</th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
        <span class="float-right pt-1">
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createNewModal">
            Create New Vehicle
          </button>          
        </span>
      </div> 
    </div>
  </div>
</div>

<div class="row pt-4">
  <div class="col-sm-4">
    <div class="card">
      <div class="card-header">Vehicle Details</div>
      <div class="card-body">
        <div class="alert alert-success" id="alertSuccess" role="alert" style="display: none;">
          Update was successful
        </div>
        <div class="alert alert-danger" id="alertError" role="alert" style="display: none;">
        </div>
                
        <form id="optionform" >
          <input id="oid" name="oid" type="hidden" class="form-control" readonly="readonly">
          <div class="form-group row">
            <label for="make" class="col-sm-3 col-form-label">Make</label>
            <div class="col-sm-7">
              <input id="make" name="make" type="text" class="form-control" readonly="readonly">
            </div>
          </div>
          <div class="form-group row">
            <label for="model" class="col-sm-3 col-form-label">Model</label>
            <div class="col-sm-7">
              <input id="model" name="model" type="text" class="form-control" readonly="readonly">
            </div>
          </div>
          <div class="form-group row">
            <label for="year" class="col-sm-3 col-form-label">Year</label>
            <div class="col-sm-7">
              <input id="year" name="year" type="text" size="5" class="form-control" readonly="readonly">
            </div>
          </div>
          <div class="form-group row">
            <label for="vin" class="col-sm-3 col-form-label">VIN</label>
            <div class="col-sm-7">
              <input id="vin" name="vin" type="text" class="form-control" readonly="readonly">
            </div>
          </div>
          <div class="form-group row">
            <label for="engineType" class="col-sm-3 col-form-label">Engine Type</label>
            <div class="col-sm-7">
              <input id="engineType" name="engineType" type="text" class="form-control" readonly="readonly">
            </div>
          </div>
          <div class="form-group row">
            <label for="odometerReading" class="col-sm-3 col-form-label">Odometer Reading</label>
            <div class="col-sm-7">
              <input id="odometerReading" name="odometerReading" type="text" class="form-control">
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
  
  <div class="col-sm-8">
    <div class="card">
      <div class="card-header">Vehicle Maintenance History</div>
      <div class="card-body">
        <table id="historyTable" class="table table-striped table-bordered" style="width:100%">
          <thead>
            <tr>
              <th width="15%">Date</th>
              <th width="85%">Work Done</th>
            </tr>
          </thead>
          <tbody>
          </tbody>
        </table>
        <span class="float-right pt-1">
          <button type="button" class="btn btn-primary" id="butAddAppoint" data-toggle="modal" data-target="#modalAddAppointment" disabled>
            Add Appointment
          </button>          
        </span>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="createNewModal" tabindex="-1" role="dialog" aria-labelledby="moModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="moModalCenterTitle">New Vehicle</h5>
      </div>
      <div class="modal-body">
        <div class="alert alert-danger" id="alertErrorCreate" role="alert" style="display: none;">
        </div>
      
        <form id="vehicleCreateForm" >
          <div class="form-group row">
            <label for="createYear" class="col-sm-3 col-form-label">Year</label>
            <div class="col-sm-7">
              <input id="createYear" name="year" type="text" size="5" class="form-control">
            </div>
          </div>
          <div class="form-group row">
            <label for="createMake" class="col-sm-3 col-form-label">Make</label>
            <div class="col-sm-7">
              <input id="createMake" name="make" type="text" size="15" class="form-control">
            </div>
          </div>
          <div class="form-group row">
            <label for="createModel" class="col-sm-3 col-form-label">Model</label>
            <div class="col-sm-7">
              <input id="createModel" name="model" type="text" size="15" class="form-control">
            </div>
          </div>
          <div class="form-group row">
            <label for="createEngineType" class="col-sm-3 col-form-label">Engine Type</label>
            <div class="col-sm-7">
              <select id="createEngineType" name="engineType" class="form-control">
              <c:forEach items="${ENGINE_TYPES}" var="type">
                <option value="${type.code}">${type.description}</option>
              </c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group row">
            <label for="createVin" class="col-sm-3 col-form-label">VIN</label>
            <div class="col-sm-7">
              <input id="createVin" name="vin" type="text" size="20" class="form-control">
            </div>
          </div>
          <div class="form-group row">
            <label for="createOdometerReading" class="col-sm-3 col-form-label">Odometer Reading</label>
            <div class="col-sm-7">
              <input id="createOdometerReading" name="odometerReading" type="text" size="20" class="form-control">
            </div>
          </div>
        </form>  
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" id="butCancel" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" id="butCreate" >Create</button>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="modalAddAppointment" tabindex="-1" role="dialog" aria-labelledby="appointmentModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="appointmentModalCenterTitle">Add Appointment</h5>
      </div>
      <div class="modal-body" id="appointmentContainer">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" id="butCancelAppointment" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" id="butCreateAppointment" >Create</button>
      </div>
    </div>
  </div>
</div>
</jsp:body>
</t:template>