var loader = '<div class="loading centerText"><h2>Loading ...</h2></div>',
    sessionMinutes = 15;

/*************** Pages **********/
function registerForm(form)
{
    var usernameContainer = $('#registerForm #username'),
        username = jQuery.trim(usernameContainer.val()),
        usernameLength = username.length,
        usernameIsValid = false;
    
    if (1 < usernameLength && usernameLength < 31)
    {
        var pattern= new RegExp('^[a-zA-Z0-9\\.\\-\\_]+$',"i");
        usernameIsValid = pattern.test(username);    
    }
    else if(usernameLength > 30)
    {
        appendErrorMessage(usernameContainer, 'Maximum length is 30 characters.');
    }    
    else
    {
        appendErrorMessage(usernameContainer, 'Use at least 2 characters');
    }
    
    var passwordContainer = $('#registerForm #password'),
        password = jQuery.trim(passwordContainer.val()),
        passwordIsValid = false;

    if (password.length > 2)
    {
        passwordIsValid = true;
    }
    else
    {
        appendErrorMessage(passwordContainer, 'Use at least 3 characters');
    }    
    
    if (usernameIsValid && passwordIsValid)
    {
        sendForm(form, username, password);
    }       
    return false;
}

function loginForm(form)
{
    var usernameContainer = $('#loginForm #username'),
        username = jQuery.trim(usernameContainer.val()),
        passwordContainer = $('#loginForm #password'),
        password = jQuery.trim(passwordContainer.val());
    
    if (username.length > 1 && password.length > 2)
    {
        sendForm(form, username, password);
    }
    else
    {
        $('.error-container').hide();
        
        if (username.length < 2)
        {
            appendErrorMessage(usernameContainer, 'Invalid Username');
        }    
        
        if (password.length < 3)
        {
            appendErrorMessage(passwordContainer, 'Invalid Password');
        }    
    }  
    
    return false;
}

function logout()
{
    checkSession();
    
    var url = 'http://mobilefriends.apphb.com/MobileFriendsService.svc/logout/' + $.session.id;
    
    $.ajax({
        url: url,
        type: 'GET',
        contentType: "application/json", 
        timeout: 10000,
        dataType: "json", 
        success: function()
        {
            $.mobile.changePage('login.html', "slideup");
        },
        error : function(error)
        {
            var message = getServerErrorMessage(error);
            alert(message);
            $.mobile.changePage('login.html', "slideup");
        }
    });
    
    return false;
}

function addFriendForm(form, prefix)
{
    sendFriendForm(form, prefix);
    
    return false;
}

function removeFriendForm(form, prefix)
{
    sendFriendForm(form, prefix);
    
    return false;
}

function sendFriendForm(form, prefix)
{ 
    var friendNameContainer = $('#friend-' + prefix),
        friendName = jQuery.trim(friendNameContainer.val());
    
    if ($.session.id)
    {
        if (friendName.length > 1)
        {
            $('.error-container').hide();
            $('.success-container').hide();
            
            var sessionID = $.session.id,
                action = form.attr('action');
            
            var userData = 
            { 
                "sessionID": sessionID, 
                "friend": friendName
            };    
            
            $.ajax({
                url: action,
                type: 'POST',
                contentType: "application/json", 
                timeout: 10000,
                dataType: "json", 
                data: JSON.stringify(userData), 
                beforeSend: ajaxCallLoader(),
                success: function()
                {
                    ajaxPrintSuccessMessage();
                    form.find('.buttonMyFriends').click();
                },
                error : ajaxCallError
            });
        }
        else
        {
            appendErrorMessage(friendNameContainer, 'Invalid Username');
        }
    }    
    else
    {
        appendErrorMessage(friendNameContainer, 'Invalid Session ID.');
    }    
}

function getFriends(container)
{
    checkSession();
    
    var url = 'http://mobilefriends.apphb.com/MobileFriendsService.svc/get-friends/' + $.session.id;   
    
    $.ajax({
        url: url,
        type: 'GET',
        contentType: "application/json", 
        timeout: 10000,
        dataType: "json",
        success: function(response)
        {
            printFriends(response.friends, container);
        },
        error : function(error)
        {
            var message = getServerErrorMessage(error);
            alert(message + ' Please login again.');
        }
    });
    
    return false;
}

function getNearFriends(form)
{
    checkSession();
    
    var userData = 
    { 
        "sessionID": $.session.id, 
        'distance' : $('#distance').val(),
        "latitude": $('#latitude').val(),
        "longitude": $('#longitude').val()
    };
    
    var action = form.attr('action');
    
    $.ajax({
        url: action,
        type: 'POST',
        contentType: "application/json", 
        timeout: 10000,
        dataType: "json", 
        data: JSON.stringify(userData), 
        beforeSend: ajaxCallLoader(),
        success: function(response)
        {
            $('.loading').remove();
            $('.ajaxCallBack').hide();
            
            printFriends(response.friends, '#list-my-friends-location');
        },
        error : ajaxCallError
    });
    
    return false;
}

function updateCoordinates()
{
    checkSession();
    
    var userData = 
    { 
        "sessionID": $.session.id, 
        "latitude": $('#latitude').val(),
        "longitude": $('#longitude').val()
    };
    
    $.ajax({
        url: 'http://mobilefriends.apphb.com/MobileFriendsService.svc/update-coordinates',
        type: 'POST',
        contentType: "application/json", 
        timeout: 10000,
        dataType: "json", 
        data: JSON.stringify(userData), 
        beforeSend: ajaxCallLoader(),
        success: function()
        {
            ajaxPrintSuccessMessage();
        },
        error : ajaxCallError
    });
}

/*************** END Pages **********/

/*************** Phonegap ***************/
function geoLocation()
{
    checkSession();
    
    navigator.geolocation.getCurrentPosition(onSuccess, onError, {maximumAge: 3000, timeout: 5000, enableHighAccuracy: true});
}
function onSuccess(position) 
{
    $('#latitude').val(position.coords.latitude);
    $('#longitude').val(position.coords.longitude);
}

function onError(error) 
{
    latitude = 'undefined';
    longitude = 'undefined';
    
    alert('code: '    + error.code    + '\n' +
          'message: ' + error.message + '\n');
}
/*************** Phonegap ***************/

function sendForm(form, username, password)
{
    $('.error-container').hide();
        
    var authCode = generateAuthCode(username, password),
        action = form.attr('action');

    var userData = 
    { 
        "username": username, 
        "authCode": authCode
    }; 

    $.ajax({
        url: action,
        type: 'POST',
        contentType: "application/json", 
        timeout: 10000,
        dataType: "json", 
        data: JSON.stringify(userData), 
        beforeSend: ajaxCallLoader(),
        success: ajaxCallSucess,
        error : ajaxCallError
    });
}

function ajaxCallLoader()
{
    $('.loading').remove();
    $('.ajaxCallBack').hide();
    $('form').after(loader);
}

function ajaxCallSucess(response)
{
    if (response.sessionID)
    {
        $('.loading').remove();
        $('.ajaxCallBack').hide();

        createNewSession(response.sessionID);
        navigateToNextPage('index.html');
    }
    else
    {
        alert('Invalid Data');
    }    
}

function ajaxPrintSuccessMessage()
{
    $('.loading').remove();
    $('.ajaxCallBack').hide();
    $('.success-container').show();
}

function ajaxCallError(error)
{
    $('.loading').remove();
    
    var message = getServerErrorMessage(error);
    
    $('.ajaxCallBack').html(message);
    $('.ajaxCallBack').show();
}

function getServerErrorMessage(error)
{
    if (error.responseText)
    {
        var serverInfo = jQuery.parseJSON(error.responseText),
            message = 'Message : ' + serverInfo.errorMsg;
    }    
    else
    {
        message = 'Please check your internet connection';
    }
        
    return message;  
}

function navigateToNextPage(url)
{
    var sessionParam = '?session=' + JSON.stringify($.session);
    
    $.mobile.changePage(url + sessionParam, "slideup");
}

function appendErrorMessage(element, message)
{
    var errorContainer = element.parent().children('.error-container');
    
    errorContainer.text(message);
    errorContainer.show();
}

function generateAuthCode(username, password)
{
    var authCode = Crypto.SHA1(username + password);
    
    return authCode;
}

function printFriends(listFriends, container)
{
    var listFriendsLength = listFriends.length; 
    
    if (listFriendsLength > 0)
    {
        var htmlCode = 
                '<form class="ui-listview-filter ui-bar-c ui-listview-filter-inset">' +
                    '<div class="ui-input-search ui-shadow-inset ui-btn-corner-all ui-btn-shadow ui-icon-searchfield ui-body-c">' + 
                        '<input class="ui-input-text ui-body-c"/>' + 
                     '</div>' + 
                '</form>';
        
        htmlCode += '<ul data-role="listview" data-inset="true" data-filter="true" class="ui-listview ui-listview-inset ui-corner-all ui-shadow">';
        
        for (var i = 0; i < listFriendsLength; i++)
        {
            var liClass = null;
            
            if (i == 0)
            {
                liClass = 'ui-corner-top';
            }
            
            if(i+1 == listFriendsLength)
            {
                if (liClass == null)
                {
                    liClass = 'ui-corner-bottom';
                }    
                else
                {
                    liClass = liClass + ' ui-corner-bottom ';
                }    
            }    
            
            htmlCode += 
                    '<li data-theme="c" class="ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ' + liClass + ' ui-btn-up-c">' + 
                        '<div class="ui-btn-inner" aria-hidden="true">' + 
                            '<div class="ui-btn-text">' +
                                '<a href="javascript:void(0);" class="ui-link-inherit">' +  listFriends[i]  + '</a>' + 
                            '</div>' + 
                        '</div>' +  
                   '</li>';
        }
        
        htmlCode += '</ul>';
        
        $(container).html(htmlCode);
        
        $(container + ' form').keyup(filterMyFriends);
    }
    else
    {
        $(container).html('Empty list');
    }    
}

function filterMyFriends()
{
    var searchCriteria = $(this).find('input').val(); 
    searchCriteria = jQuery.trim(searchCriteria);
    searchCriteria = searchCriteria.replace(/[\[\]()\\"'']+/g,'');
    
    var pattern= new RegExp(searchCriteria,"i"),
        matcher = false;
    
    var list = $(this).parent().children('ul');
    
    list.children('li').each(function()
    {
        var recordText = jQuery.trim($(this).find('a').text());
        
        matcher = pattern.test(recordText);
            
        if(matcher)
        {
            $(this).show();
        }
        else
        {
            $(this).hide();
        }
    });
}

/*************** Session ***************/

function createNewSession(sessionId)
{
    $.session.id = sessionId;
    var date = new Date();
    
    date.setMinutes(date.getMinutes() + sessionMinutes);
    
    $.session.date = date.getTime(); 
}

function appendSessionDetailsToUrl(link)
{
    checkSession();
    
    var sessionParam = '?session=' + JSON.stringify($.session),
        href = link.attr('href') + sessionParam;

    link.attr('href', href);
    
    return true;
} 
function createSessionParamsFromUlr()
{
    var sesionParams = window.location.search;
    
    if (sesionParams.length > 0)
    {
        sesionParams = sesionParams.replace("?session=","");
    
        sesionParams = unescape(sesionParams); 

        var session = jQuery.parseJSON(sesionParams);

        if (!$.session)
        {
            $.session = {'id' : null, 'date' : null};  
        }    

        if (session.id && session.date)
        {
            $.session.id = session.id;
            $.session.date = session.date;
        }
    }
    
}

function checkSession()
{
    createSessionParamsFromUlr();
    
    var sessionIsValid = false;
    
    if ($.session)
    {
        if ($.session.id && $.session.date)
        {
            var currentDate = new Date(),
                currentDateMilliseconds = currentDate.getTime(),
                sessionDate = new Date($.session.date);
            
            if (currentDateMilliseconds <= sessionDate)
            {
                sessionIsValid = true;
                
                $.session.date = currentDateMilliseconds + (1000 * 60 * sessionMinutes); 
            }    
        }    
    }
    
    if (!sessionIsValid)
    {
        //var domain = document.domain;
        //window.location = 'http://127.0.0.1/login.html';
        
        window.location = 'file:///android_asset/assets/www/login.html';
    }      
}

function destroySession()
{
    $.session.id = null;
    $.session.date = null;
}

/*************** Session ***************/