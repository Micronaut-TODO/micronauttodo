@import io.micronaut.bootstrap.Alert
@import io.micronaut.context.LocalizedMessageSource
@param LocalizedMessageSource messageSource
@param Alert alert
<div class="alert alert-${alert.variant().toString()} alert-dismissible fade show mt-3" role="alert">
    <span>${alert.message().code() == null ? alert.message().defaultMessage() : messageSource.getMessageOrDefault(alert.message().code(), alert.message().defaultMessage())}</span>
@if(alert.dismissible())
    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
@endif
</div>