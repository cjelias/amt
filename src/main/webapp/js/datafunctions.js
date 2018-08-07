function bindField(field) {
  var fieldName = '#'+field; 
  $(fieldName).bind("change paste keyup", function() {
    $('#butUpdate').disable(false);
    map[field] = $(this).val();
  });
}

function unbindField(field) {
  var fieldName = '#'+field; 
  $(fieldName).unbind("change paste keyup");
}

function convertFormToJSON(form){
  var array = jQuery(form).serializeArray();
  var json = {};
    
  jQuery.each(array, function() {
      json[this.name] = this.value || '';
  });
    
  return json;
}