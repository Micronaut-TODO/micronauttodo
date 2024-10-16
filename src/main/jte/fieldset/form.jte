@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.Form
@param Form form
@param LocalizedMessageSource messageSource
@if(form.dataturbo() != null)
<form action="${form.action()}" method="${form.method()}" enctype="${form.enctype()}" data-turbo="${form.dataturbo() ? "true": "false"}">
    @template.fieldset.fieldset(el = form.fieldset(), messageSource = messageSource)
</form>
@else
<form action="${form.action()}" method="${form.method()}" enctype="${form.enctype()}">
    @template.fieldset.fieldset(el = form.fieldset(), messageSource = messageSource)
</form>
@endif

