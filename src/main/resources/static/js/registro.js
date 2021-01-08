$(document).ready(function(){
    $.ajaxSetup({contentType: "application/json; charset=utf-8"});
    //Método para registrar un entrenador
    $("#post").click(function(){
        
        var opt= valida_campos();
        if(opt == true){
            
            var entrenador = {
                nombre:$("#nombres").val(),
                mail:$("#emailConfirmado").val(),
                direccion:$("#calle").val(),
                psswrd:$("#passwordC").val()
            };
            var entrenadorJSON=JSON.stringify(entrenador);   
            console.log("Entrenador= "+entrenadorJSON);
            var url="http://localhost:8080/alumnos"
            $.post(url,entrenadorJSON,function(data,status){
                $("#resultado").html(""+status);
            })
            .fail(function (){
                $("#resultado").html("No puedes registrar a 2 alumnos con la misma matricula");
            })

        }
    });
}
