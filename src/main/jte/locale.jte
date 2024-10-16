@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.Form
@param Form localeForm
@param LocalizedMessageSource messageSource
<div class="dropdown position-fixed bottom-0 start-0 mb-3 ms-3 bd-mode-toggle">
    @template.fieldset.form(form = localeForm, messageSource = messageSource)
</div>