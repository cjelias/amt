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
  var arrayData, objectData;
  arrayData = form.serializeArray();
  objectData = {};

  $.each(arrayData, function() {
    var value;

    if (this.value != null) {
      value = this.value;
    } else {
      value = '';
    }

    if (objectData[this.name] != null) {
      if (!objectData[this.name].push) {
        objectData[this.name] = [objectData[this.name]];
      }

      objectData[this.name].push(value);
    } else {
      objectData[this.name] = value;
    }
  });

  return objectData;}