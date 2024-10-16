@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.InputTelFormElement
@import io.micronaut.views.fields.elements.InputStringFormElement
@param InputTelFormElement el
@param LocalizedMessageSource messageSource
@if(el instanceof InputStringFormElement inputStringFormElement)
@template.fieldset.inputstring(type = "tel", el = inputStringFormElement, messageSource = messageSource)
@endif

