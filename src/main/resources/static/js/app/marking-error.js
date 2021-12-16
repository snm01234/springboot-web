var markingErrorField = function (response) {
const errorFields = response.responseJSON.errors;

    if(!errorFields){
        alert(response.response.message);
        return;
    }

    var $field, error;
    for(var i=0, length = errorFields.length; i<length;i++){
        error = errorFields[i];
        $field = $('#'+error['field']);

        if($field && $field.length > 0){
            $field.siblings('.error-message').remove();
            $field.after('<span class="error-message" style="color:red">'+error.defaultMessage+'</span>');
        }
    }
};