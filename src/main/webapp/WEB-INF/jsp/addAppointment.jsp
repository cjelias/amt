<%@ page session="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form id="addAppointmentForm">
  <div class="form-group row">
    <label for="createMake" class="col-sm-4 col-form-label">Appointment Date</label>
    <div class="col-sm-7">
      <input type="text" id="appointmentDate" name="appointmentDate" />
    </div>
  </div>

  <fieldset class="form-group">
  <div class="form-group row">
    <div class="col-sm-4">Maintenance Options</div>
    <div class="col-sm-7">
        <c:forEach items="${OPTIONS}" var="option">
        <div class="form-check">
          <input class="form-check-input" type="checkbox" name="appointmentOption" value="${option.code}" id="${option.code}Opt">
          <label class="form-check-label" for="${option.code}Opt">
            ${option.description}
          </label>
        </div>
        </c:forEach>
    </div>
  </div>
  </fieldset>  
</form>

<script>
$('#appointmentDate').daterangepicker({
  "singleDatePicker": true,
  "timePicker": true,
  "locale": {
    "format": "YYYY/MM/DD HH:mm A"
  }
}, function(start, end, label) {
});
</script>