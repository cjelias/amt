<%@ page session="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!-- Check out for downloads -->

<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="uri" value="${req.requestURI}" />

<!DOCTYPE html>
<html lang="en">
<head>
  <base href="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/">
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Automobile Maintenance Tracker</title>
  <link rel="stylesheet" href="/css/amt.css">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css"/>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/css/bootstrap-select.min.css">
  <link rel="stylesheet" href="https://cdn.datatables.net/select/1.2.7/css/select.dataTables.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ajax-bootstrap-select/1.4.4/css/ajax-bootstrap-select.min.css">
</head>

<body class="bg-light">
  <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
    <a class="navbar-brand" href="#">Automobile Maintenance Tracker</a>
    <button class="navbar-toggler p-0 border-0" type="button" data-toggle="offcanvas">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarsAmt">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item" id="menuSchedule" data-page="scheduler"><a class="nav-link" href="#/scheduler">Schedule</a></li>
        <li class="nav-item" id="menuVehicles" data-page="vehicles"><a class="nav-link" href="#/vehicles">Vehicles</a></li>
          
        <li class="nav-item dropdown" id="menuSettings">
          <a class="nav-link dropdown-toggle" href="#" id="ddSettings" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Settings</a>
          <div class="dropdown-menu" aria-labelledby="ddSettings">
            <a class="dropdown-item" parent="menuSettings" id="enginetype" data-page="settings/enginetype" href="#/settings/enginetype">Engine Type</a>
            <a class="dropdown-item" parent="menuSettings" id="maintenanceoptions" data-page="settings/maintenanceoptions" href="#/settings/maintenanceoptions">Maintenance Options</a>
          </div>
        </li>          
      </ul>
      <a class="btn btn-outline-light" href="/logout">Logout</a>
    </div>
  </nav>
  
  <div class="container-fluid pt-2">
  </div>
  
  <script src="https://code.jquery.com/jquery-3.3.1.min.js" integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>  
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>  

  <script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
  <script src="https://cdn.datatables.net/select/1.2.7/js/dataTables.select.min.js"></script>
  <script src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js"></script>
  <script src="/js/amt.js"></script>
  
</body>
</html>