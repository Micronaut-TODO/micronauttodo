@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.InputStringFormElement
@param String type
@param InputStringFormElement el
@param LocalizedMessageSource messageSource
@template.fieldset.label(id = el.id(), el = el.label(), messageSource = messageSource)
@if(el.errors().size() > 0)
<input
    type="${type}"
    name="${el.name()}"
    value="${el.value()}"
    id="${el.id()}"
    placeholder="${el.placeholder()}"
    pattern="${el.pattern()}"
    minlength="${el.minLength()}"
    maxlength="${el.maxLength()}"
    size="${el.size()}"
    readOnly="${el.readOnly()}"
    class="form-control is-invalid"
    aria-describedby="${el.name()}ValidationServerFeedback"
    required="${el.required()}"/>
    @template.fieldset.errors(name = el.name(), errors = el.errors(), messageSource = messageSource)
@else
<input
    type="${type}"
    name="${el.name()}"
    value="${el.value()}"
    id="${el.id()}"
    placeholder="${el.placeholder()}"
    pattern="${el.pattern()}"
    minlength="${el.minLength()}"
    maxlength="${el.maxLength()}"
    size="${el.size()}"
    readOnly="${el.readOnly()}"
    class="form-control"
    required="${el.required()}"/>
@endif
