var localeEl = document.getElementById('locale');
console.log("locale" + localeEl)
if(!(localeEl === null || localeEl === undefined)) {
    localeEl.addEventListener('change', function () {
        const forms = document.getElementsByTagName('form');
        for (let i = 0; i < forms.length; i++) {
            if (forms[i].getAttribute('action') === '/locale') {
                forms[i].submit();
                break;
            }
        }
    });
}