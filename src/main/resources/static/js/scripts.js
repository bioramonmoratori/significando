document.addEventListener('DOMContentLoaded', function () {
    const relacionarSentimentoButton = document.getElementById('relacionarSentimentoButton');
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    const btnRelacionar = document.getElementById("btn-relacionar");
    

    function onSubmit(){
        // Verifica se pelo menos um checkbox está marcado
        const algumCheckboxMarcado = Array.from(checkboxes).some(function (checkbox) {
            return checkbox.checked;
        });

        if (algumCheckboxMarcado) {
            // Pelo menos um checkbox está marcado, então envie o formulário
            document.getElementById('sentimentos-form').submit();
        } else {
            // Nenhum checkbox está marcado, exiba uma mensagem de erro ou realize outra ação desejada
            alert('Selecione pelo menos um sentimento antes de continuar.');
        }
    }

    
    btnRelacionar.addEventListener("click", onSubmit)
    relacionarSentimentoButton.addEventListener('click', onSubmit);
});