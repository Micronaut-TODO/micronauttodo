@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.InputFileFormElement
@param InputFileFormElement el
@param LocalizedMessageSource messageSource
@template.fieldset.label(id = el.id(), el = el.label(), messageSource = messageSource)
@if(el.hasErrors())
<input type="file"
    name="${el.name()}"
    id="${el.id()}"
    accept="${el.accept()}"
    class="form-control is-invalid"
    aria-describedby="${el.name()}ValidationServerFeedback"
    required="${el.required()}"
    />
    @template.fieldset.errors(name = el.name(), errors = el.errors(), messageSource = messageSource)
@else
<input type="file"
    name="${el.name()}"
    id="${el.id()}"
    accept="${el.accept()}"
    class="form-control"
    required="${el.required()}"
    />
@endif
