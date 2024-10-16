@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.TextareaFormElement
@param TextareaFormElement el
@param LocalizedMessageSource messageSource
@template.fieldset.label(id = el.id(), el = el.label(), messageSource = messageSource)
@if(el.hasErrors())
<textarea
    name="${el.name()}"
    id="${el.id()}"
    cols="${el.cols()}"
    rows="${el.rows()}"
    class="form-control is-invalid"
    aria-describedby="${el.name()}ValidationServerFeedback">${el.value()}</textarea>
    @template.fieldset.errors(name = el.name(), errors = el.errors(), messageSource = messageSource)
@else
<textarea
    name="${el.name()}"
    id="${el.id()}"
    cols="${el.cols()}"
    rows="${el.rows()}"
    class="form-control">${el.value()}</textarea>
@endif

