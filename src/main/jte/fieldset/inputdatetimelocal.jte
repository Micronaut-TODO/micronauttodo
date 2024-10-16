@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.InputDateTimeLocalFormElement
@param InputDateTimeLocalFormElement el
@param LocalizedMessageSource messageSource
@template.fieldset.label(id = el.id(), el = el.label(), messageSource = messageSource)
@if(el.hasErrors())
<input
    type="datetime-local"
    name="${el.name()}"
    value="${el.value() != null ? el.value().toString() : null}"
    id="${el.id()}"
    min="${el.min() != null ? el.min().toString() : null}"
    max="${el.max() != null ? el.max().toString() : null}"
    step="${el.step()}"
    class="form-control is-invalid"
    aria-describedby="${el.name()}ValidationServerFeedback"
    required="${el.required()}"
    />
    @template.fieldset.errors(el.name(), el.errors(), messageSource = messageSource)
@else
<input
    type="datetime-local"
    name="${el.name()}"
    value="${el.value() != null ? el.value().toString() : null}"
    id="${el.id()}"
    min="${el.min() != null ? el.min().toString() : null}"
    max="${el.max() != null ? el.max().toString() : null}"
    step="${el.step()}"
    class="form-control"
    required="${el.required()}"/>
@endif
