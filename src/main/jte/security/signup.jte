@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.Form
@param Form form
@param Form localeForm
@param LocalizedMessageSource messageSource
@param String csrfToken
<!doctype html>
<html lang="en" data-bs-theme="auto">
<head>
    <title>Sign Up </title>
    @template.htmlHead(csrfToken = csrfToken)
    <link href="/assets/stylesheets/sign-in.css" rel="stylesheet">
    <script>
        window.onload = function() {
            document.getElementById('email').focus();
    };
    </script>
</head>
<body class="d-flex align-items-center py-4 bg-body-tertiary">
@template.appearance()
@template.locale(localeForm = localeForm, messageSource = messageSource)
<main class="form-signin w-100 m-auto">
    @template.fieldset.form(form = form, messageSource = messageSource)
    <p class="mt-5 mb-3 text-body-secondary"><a href="/security/login">${messageSource.getMessageOrDefault("loginform.submit", "Log In")}</a></p>
</main>
@template.htmlBodyScript()
</body>
</html>
