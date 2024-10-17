@param String csrfToken
@if(csrfToken != null)
<meta name="csrf-token" content="${csrfToken}">
@endif
<script src="/assets/javascripts/bootstrap-5.3.3-dist/color-modes.js"></script>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/assets/stylesheets/bootstrap-5.3.3-dist/bootstrap.min.css" rel="stylesheet"/>
<meta name="theme-color" content="#712cf9">
<link href="/assets/stylesheets/app.css" rel="stylesheet"/>
<link href="/assets/stylesheets/sign-in.css" rel="stylesheet">
<script type="module">
    import * as Turbo from '/assets/javascripts/turbo8.0.4/turbo.min.js';
</script>
<script type="module">
    import {Application, Controller} from "/assets/javascripts/stimulus-3.2.1.js"
    window.Stimulus = Application.start()
    Stimulus.register("reset-form", class extends Controller {
        reset() {
            this.element.reset()
        }
    })
</script>