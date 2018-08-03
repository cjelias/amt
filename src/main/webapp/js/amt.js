jQuery.fn.extend({
  disable: function(state) {
    return this.each(function() {
      this.disabled = state;
    });
  }
});
  
var routes = {};
var container = $(".container-fluid");

function route(id, uri, parent) {
  routes[uri] = { id: id, parent: parent };
}

function router() {
  var uri = location.hash.substring(2) || 'scheduler';
  var route = routes[uri];
  console.log("Route id: " + uri);
   
  if (route && container) {
    console.log("Loading...." + uri);
    container.load('/app/controller/' +  uri);
      
    $('.nav-item').removeClass('active');
    $('.dropdown-item').removeClass('active');
    $("#" + route.id ).addClass('active');
    $("#" + route.parent ).addClass('active');
  }
}

function convertFormToJSON(form){
  var array = jQuery(form).serializeArray();
  var json = {};
    
  jQuery.each(array, function() {
      json[this.name] = this.value || '';
  });
    
  return json;
}

$(document).ready(function() {
  $(".nav-item").each(function() {
    var idAttr = $(this).attr("id");
    var dataPageAttr = $(this).attr("data-page");
        
    if (typeof dataPageAttr !== typeof undefined && dataPageAttr !== false) {
      console.log("dropdown-item: " + idAttr + " data-page: " + dataPageAttr );
      route( idAttr, dataPageAttr );
    }
  });

  $(".dropdown-item").each(function() {
    var idAttr = $(this).attr("id");
    var dataPageAttr = $(this).attr("data-page");
    var parentAttr = $(this).attr("parent");
        
    if (typeof dataPageAttr !== typeof undefined && dataPageAttr !== false) {
      console.log("dropdown-item: " + idAttr + " data-page: " + dataPageAttr + " parent: " + parentAttr );
      route( idAttr, dataPageAttr, parentAttr );
    }
  });

  $(window).bind('hashchange', router);
  router();
});

$(function () {
  'use strict'
  $('[data-toggle="offcanvas"]').on('click', function () {
    $('.offcanvas-collapse').toggleClass('open')
  })
})