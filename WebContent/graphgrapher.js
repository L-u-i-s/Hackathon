var gg = ( function () {
    
    var context = "aún no carga";
	 
    var inicializar = function () {	
	context = document.getElementById( "miCanvas" ).getContext('2d');
    };

    var g = ( function () {

	nodo = {
	    fill: 'white',
	    line: 'black',
	    radius: 10
	};

	return {
	    nodo : nodo
	};

    })();

    var geometry = ( function () {

	var drawText = function (text, x, y, fill) {
	    context.fillStyle = fill;
	    context.textAlign = 'center';
	    context.textBaseline = 'middle';
	    context.fillText(text, x, y);
	};

	var drawCircle = function (radius, x, y, line, fill) {
	    context.beginPath();
	    context.arc(x, y, radius, 0, 2*Math.PI);
	    context.fillStyle = fill;
	    context.fill();
	    context.strokeStyle = line;
	    context.stroke();
	};

	return {
	    drawText : drawText,
	    drawCircle : drawCircle
	};

    })();

    var nodo =  function () {

	var label;

	var position = {
	    x: 0,
	    y: 0
	};
	
	var setLabel = function (text) {
	    this.label = text;
	};
	
	var getLabel = function(nodo){
		
		
	}


	var draw = function() {
	    geometry.drawCircle(g.nodo.radius, this.position.x, this.position.y, 
				g.nodo.line, g.nodo.fill);
	    geometry.drawText(this.label, this.position.x, this.position.y, 
			      g.nodo.line);
	};

	return {
	    position : position,
	    setLabel : setLabel,
	    draw : draw
	};

    };

    var nodos = [];
    var arcos = [];

    var putNodo = function (x, y) {
	nodos.push(nodo());
	var i = nodos.length-1;
	nodos[i].position.x = x;
	nodos[i].position.y = y;
	nodos[i].setLabel(i);
	return i;
    };

    var plug = function (a, b) {
		
		context.strokeStyle = 'purple';
		context.lineWidth = 4;
      context.stroke();
	arcos.push([a,b]);
    };

    var unplug = function (a, b) {
	for(var i=0; i<arcos.length; i++) {
	    if((arcos[i][0] == a && arcos[i][1] == b) ||
	       (arcos[i][1] == a && arcos[i][0] == b))
		arcos.splice(i,1);
	}
    };

    var drawAll = function () {

	for(var i=0; i<arcos.length; i++) {
	    var origin = nodos[arcos[i][0]], destiny = nodos[arcos[i][1]];
	    context.moveTo(origin.position.x, origin.position.y);
	    context.lineTo(destiny.position.x, destiny.position.y);
	    context.stroke();
	}

	for(var i=0; i<nodos.length; i++) {
	    nodos[i].draw();
	}

    };

    var eraseAll = function () {
	var canvas = document.getElementById( "miCanvas" );
	context.beginPath();
	context.position(400,500);
	context.rect(-10, -10, canvas.width+100, canvas.height+100);

	context.fillStyle = 'white';
	context.fill();
    };

    return {
	context : context,
	opciones_graficas : g,
	figuras_geometricas : geometry,
	Nodo : nodo,
	nodos : nodos,
	arcos : arcos,
	ponerNodo : putNodo,
	conectar : plug,
	desconectar: unplug,
	dibujarTodo: drawAll,
	borrarTodo: eraseAll,
	inicializar : inicializar
    };

})();
