var Graph = (function (undefined) {

    var extractKeys = function (obj) {
		var keys = [], key;
		for (key in obj) {
		    Object.prototype.hasOwnProperty.call(obj,key) && keys.push(key);
		}
		return keys;
	}

	var sorter = function (a, b) {
		return parseFloat (a) - parseFloat (b);
	}

	var findPaths = function (map, start, end, infinity) {
		infinity = infinity || Infinity;

		var costs = {},
		    open = {'0': [start]},
		    predecessors = {},
		    keys;

		var addToOpen = function (cost, vertex) {
			var key = "" + cost;
			if (!open[key]) open[key] = [];
			open[key].push(vertex);
		}

		costs[start] = 0;

		while (open) {
			if(!(keys = extractKeys(open)).length) break;

			keys.sort(sorter);

			var key = keys[0],
			    bucket = open[key],
			    node = bucket.shift(),
			    currentCost = parseFloat(key),
			    adjacentNodes = map[node] || {};

			if (!bucket.length) delete open[key];

			for (var vertex in adjacentNodes) {
			    if (Object.prototype.hasOwnProperty.call(adjacentNodes, vertex)) {
					var cost = adjacentNodes[vertex],
					    totalCost = cost + currentCost,
					    vertexCost = costs[vertex];

					if ((vertexCost === undefined) || (vertexCost > totalCost)) {
						costs[vertex] = totalCost;
						addToOpen(totalCost, vertex);
						predecessors[vertex] = node;
					}
				}
			}
		}

		if (costs[end] === undefined) {
			return null;
		} else {
			return predecessors;
		}

	}

	var extractShortest = function (predecessors, end) {
		var nodes = [],
		    u = end;

		while (u) {
			nodes.push(u);
			u = predecessors[u];
		}

		nodes.reverse();
		return nodes;
	}

	var findShortestPath = function (map, nodes) {
		var start = nodes.shift(),
		    end,
		    predecessors,
		    path = [],
		    shortest;

		while (nodes.length) {
			end = nodes.shift();
			predecessors = findPaths(map, start, end);

			if (predecessors) {
				shortest = extractShortest(predecessors, end);
				if (nodes.length) {
					path.push.apply(path, shortest.slice(0, -1));
				} else {
					return path.concat(shortest);
				}
			} else {
				return null;
			}

			start = end;
		}
	}

	var toArray = function (list, offset) {
		try {
			return Array.prototype.slice.call(list, offset);
		} catch (e) {
			var a = [];
			for (var i = offset || 0, l = list.length; i < l; ++i) {
				a.push(list[i]);
			}
			return a;
		}
	}

	var Graph = function (map) {
		this.map = map;
	}

	Graph.prototype.findShortestPath = function (start, end) {
		if (Object.prototype.toString.call(start) === '[object Array]') {
			return findShortestPath(this.map, start);
		} else if (arguments.length === 2) {
			return findShortestPath(this.map, [start, end]);
		} else {
			return findShortestPath(this.map, toArray(arguments));
		}
	}

	Graph.findShortestPath = function (map, start, end) {
		if (Object.prototype.toString.call(start) === '[object Array]') {
			return findShortestPath(map, start);
		} else if (arguments.length === 3) {
			return findShortestPath(map, [start, end]);
		} else {
			return findShortestPath(map, toArray(arguments, 1));
		}
	}

	return Graph;

})();

//var map = {a:{b:3,c:1},b:{a:2,c:1},c:{a:4,b:1}},
var map = {a:{b:1},b:{a:1,c:1,d:1,j:1},c:{b:1},d:{e:1,b:1},e:{d:1,f:1,l:1},f:{e:1,g:1},g:{f:1,h:1,n:1},h:{g:1},i:{j:1},j:{k:1,i:1,q:1},k:{j:1,l:1},l:{k:1,m:1,s:1},m:{l:1,n:1},n:{m:1,o:1,u:1}, o:{n:1},p:{q:1},q:{p:1,r:1,x:1},r:{s:1,q:1},s:{t:1,r:1,z:1},t:{u:1,s:1},u:{v:1,t:1,b1:1},v:{u:1},w:{x:1},x:{w:1,y:1,q:1},y:{x:1,z:1},z:{y:1,a1:1},a1:{z:1,b1:1},b1:{a1:1,c1:1,d1:1},c1:{b1:1}},
//
			    graph = new Graph(map);
			console.log(graph.findShortestPath('a', 'v'));
			document.write(Graph)