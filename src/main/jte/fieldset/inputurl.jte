@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.InputUrlFormElement
@import io.micronaut.views.fields.elements.InputStringFormElement
@param InputUrlFormElement el
@param LocalizedMessageSource messageSource
@if(el instanceof InputStringFormElement inputStringFormElement)
@template.fieldset.inputstring(type = "url", el = inputStringFormElement, messageSource = messageSource)
@endif

