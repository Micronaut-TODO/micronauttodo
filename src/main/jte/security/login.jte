@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.Form
@import io.micronaut.bootstrap.Alert
@param Alert alert
@param Form form
@param LocalizedMessageSource messageSource
@param Form localeForm
@param String csrfToken
<!doctype html>
<html lang="en" data-bs-theme="auto">
<head>
    <title>Sign In </title>
    @template.htmlHead(csrfToken = csrfToken)
    <link href="/assets/stylesheets/sign-in.css" rel="stylesheet">
    <script>
    window.onload = function() {
        document.getElementById('username').focus();
    };
    </script>
</head>
<body class="d-flex align-items-center py-4 bg-body-tertiary">
@template.appearance()
@template.locale(localeForm = localeForm, messageSource = messageSource)
<main class="form-signin w-100 m-auto">
    @template.fieldset.form(form = form, messageSource = messageSource)
    @if(alert != null)
    @template.bootstrap.alert(alert = alert, messageSource = messageSource)
    @endif
    <p class="mt-5 mb-3 text-body-secondary"><a href="/security/signup">${messageSource.getMessageOrDefault("signupform.submit", "Sign Up")}</a></p>
</main>
@template.htmlBodyScript()
</body>
</html>
