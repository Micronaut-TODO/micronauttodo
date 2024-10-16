@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.InputTextFormElement
@import io.micronaut.views.fields.elements.InputStringFormElement
@param InputTextFormElement el
@param LocalizedMessageSource messageSource
@if(el instanceof InputStringFormElement inputStringFormElement)
@template.fieldset.inputstring(type = "text", el = inputStringFormElement, messageSource = messageSource)
@endif

