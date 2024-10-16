@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.InputSubmitFormElement
@param InputSubmitFormElement el
@param LocalizedMessageSource messageSource
<input type="submit"
 value="${el.value().code() == null ? el.value().defaultMessage() : messageSource.getMessageOrDefault(el.value().code(), el.value().defaultMessage())}"
 class="btn btn-primary"/>

