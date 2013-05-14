
function createContact(form)
{
    // create a new contact object
    var contact = navigator.contacts.create();
    
    contact.displayName = form.find('#display_name').val();
    contact.nickname = form.find('#nick_name').val();
    
    var name = new ContactName();
    name.givenName = form.find('#given_name').val();
    name.familyName = form.find('#family_name').val();
    contact.name = name;
    
    var phoneNumbers = new Array();
    $('.phoneNumbers').each(function(i)
    {
        var phoneType = $(this).children('fieldset').eq(0).find('input[type="radio"]:checked').val();
        var phoneNumber = $(this).find('input[type="text"]').val();
        var preferredNumber = $(this).children('fieldset').eq(1).find('input[type="radio"]:checked').val();

        phoneNumbers[i] = new ContactField(phoneType, phoneNumber, preferredNumber);
    });
    
    contact.phoneNumbers = phoneNumbers;
    
    contact.save(onSaveSuccess, onSaveError);
    
    return false;
}

function findContact(field)
{
    var fieldValue = field.val();
    
    if (fieldValue.length > 0)
    {
        var options = new ContactFindOptions();
        options.filter = fieldValue;
        var fields = ["displayName", "name"];
        navigator.contacts.find(fields, onSuccess, onError, options);
    } 
}

//onSuccess: Get a snapshot of the current contacts
//
function onSuccess(contacts) 
{
    if (contacts.length > 0)
    {    
        var contact = null, 
            htmlCode = '<ul>';
        
        htmlCode += 'contacts : ' + contacts.length;
        
        for (var i=0; i < contacts.length; i++) 
        {
            contact = contacts[i];
            
            htmlCode += 
                    '<li>' +
                        '<div>Display Name : ' + contact.displayName + '</div>' + 
                        '<div>First Name : ' + contact.name.givenName + '</div>' + 
                        '<div>Last Name : ' + contact.name.familyName + '</div>' + 
                    '</li>';
        }
        
        htmlCode += '</ul>';
        
        $('#myContacts').html(htmlCode);
    }
    else
    {
        $('#myContacts').html("contacts : 0");
    }    
}

// onError: Failed to get the contacts
//
function onError(contactError) 
{
    alert('onError!');
}

//onSaveSuccess: Get a snapshot of the current contacts
//
function onSaveSuccess(contact) 
{
    alert("Save Success");
}

// onSaveError: Failed to get the contacts
//
function onSaveError(contactError) 
{
    alert("Error = " + contactError.code);
}
