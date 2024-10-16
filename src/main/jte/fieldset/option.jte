@import io.micronaut.context.LocalizedMessageSource
@import io.micronaut.views.fields.elements.Option
@param Option el
@param LocalizedMessageSource messageSource
<option value="${el.value()}" selected="${el.selected()}" disabled="${el.disabled()}">${el.label().code() == null ? el.label().defaultMessage() : messageSource.getMessageOrDefault(el.label().code(), el.label().defaultMessage())}</option>
