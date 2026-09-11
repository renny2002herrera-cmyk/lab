public class MiListaDoble implements ListInterface {
    DoubleNode cabeza = null;

    @Override
    public boolean isEmpty() {
        return cabeza == null;
    }

    @Override
    public int getSize() {
        DoubleNode iterador = cabeza;
        int contador = 0;

        while (iterador != null) {
            contador++;
            iterador = iterador.siguiente;
        }
        return contador;
    }

    @Override
    public void clear() {
        cabeza = null;
    }

    @Override
    public Object getHead() {
        if (cabeza == null) {
            return null;
        }
        return cabeza.dato;
    }

    @Override
    public Object getTail() {
        if (cabeza == null) {
            return null;
        }

        DoubleNode iterador = cabeza;
        while (iterador.siguiente != null) {
            iterador = iterador.siguiente;
        }
        return iterador.dato;
    }

    @Override
    public Object get(DoubleNode node) {
        if (node == null) {
            return null;
        }
        return node.dato;
    }

    @Override
    public DoubleNode search(Object object) {
        if (object == null) {
            return null;
        }

        DoubleNode iterador = cabeza;
        while (iterador != null) {
            if (iterador.dato.equals(object)) {
                return iterador;
            }
            iterador = iterador.siguiente;
        }
        return null;
    }

    @Override
    public boolean add(Object object) {
        if (object == null) return false;

        DoubleNode nuevo = new DoubleNode(object);

        if (cabeza == null) {
            cabeza = nuevo;
            return true;
        }

        DoubleNode actual = cabeza;
        while (actual.siguiente != null) {
            actual = actual.siguiente;
        }

        actual.siguiente = nuevo;
        nuevo.anterior = actual;

        return true;
    }

    @Override
    public boolean insert(DoubleNode node, Object object) {
        if (node == null || object == null) return false;

        DoubleNode actual = cabeza;
        while (actual != null) {
            if (actual == node) {
                DoubleNode nuevo = new DoubleNode(object);
                DoubleNode siguienteOriginal = node.siguiente;

                nuevo.anterior = node;
                nuevo.siguiente = siguienteOriginal;
                node.siguiente = nuevo;

                if (siguienteOriginal != null) {
                    siguienteOriginal.anterior = nuevo;
                }

                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    @Override
    public boolean insert(Object objectRef, Object object) {
        DoubleNode nodoReferencia = search(objectRef);

        if (nodoReferencia == null) {
            return false;
        }
        return insert(nodoReferencia, object);
    }

    @Override
    public boolean insertHead(Object object) {
        if (object == null) return false;

        DoubleNode nuevo = new DoubleNode(object);

        if (cabeza == null) {
            cabeza = nuevo;
            return true;
        }

        nuevo.siguiente = cabeza;
        cabeza.anterior = nuevo;
        cabeza = nuevo;

        return true;
    }

    @Override
    public boolean insertTail(Object object) {
        if (object == null) return false;

        DoubleNode nuevo = new DoubleNode(object);

        if (cabeza == null) {
            cabeza = nuevo;
            return true;
        }

        DoubleNode actual = cabeza;
        while (actual.siguiente != null) {
            actual = actual.siguiente;
        }

        actual.siguiente = nuevo;
        nuevo.anterior = actual;

        return true;
    }

    @Override
    public boolean set(DoubleNode node, Object object) {
        if (node == null || object == null) return false;

        DoubleNode actual = cabeza;
        while (actual != null) {
            if (actual == node) {
                actual.dato = object;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    @Override
    public boolean remove(DoubleNode node) {
        if (node == null || cabeza == null) return false;

        // Verificamos que el nodo pertenezca a esta lista
        DoubleNode actual = cabeza;
        boolean pertenece = false;
        while (actual != null) {
            if (actual == node) {
                pertenece = true;
                break;
            }
            actual = actual.siguiente;
        }
        if (!pertenece) return false;

        // Caso especial: es la cabeza
        if (node == cabeza) {
            cabeza = node.siguiente;
            if (cabeza != null) {
                cabeza.anterior = null;
            }
            return true;
        }

        DoubleNode anteriorNodo = node.anterior;
        DoubleNode siguienteNodo = node.siguiente;

        anteriorNodo.siguiente = siguienteNodo;

        if (siguienteNodo != null) {
            siguienteNodo.anterior = anteriorNodo;
        }

        return true;
    }

    @Override
    public boolean contains(Object object) {
        return search(object) != null;
    }

    @Override
    public Object[] toArray() {
        Object[] arreglo = new Object[getSize()];
        DoubleNode actual = cabeza;
        int i = 0;

        while (actual != null) {
            arreglo[i] = actual.dato;
            i++;
            actual = actual.siguiente;
        }
        return arreglo;
    }

    @Override
    public Object[] toArray(Object[] object) {
        int size = getSize();

        if (object.length < size) {
            object = new Object[size];
        }

        DoubleNode actual = cabeza;
        int i = 0;

        while (actual != null) {
            object[i] = actual.dato;
            i++;
            actual = actual.siguiente;
        }

        if (object.length > size) {
            object[size] = null;
        }

        return object;
    }

    @Override
    public MiListaDoble subList(DoubleNode from, DoubleNode to) {
        if (from == null || to == null) return null;

        MiListaDoble sub = new MiListaDoble();
        DoubleNode actual = from;
        boolean encontrado = false;

        while (actual != null) {
            sub.insertTail(actual.dato);
            if (actual == to) {
                encontrado = true;
                break;
            }
            actual = actual.siguiente;
        }

        return encontrado ? sub : null;
    }

    @Override
    public MiListaDoble sortList() {
        MiListaDoble ordenada = new MiListaDoble();
        Object[] arreglo = this.toArray();

        for (int i = 0; i < arreglo.length - 1; i++) {
            for (int j = 0; j < arreglo.length - 1 - i; j++) {
                Comparable actual = (Comparable) arreglo[j];
                if (actual.compareTo(arreglo[j + 1]) > 0) {
                    Object temp = arreglo[j];
                    arreglo[j] = arreglo[j + 1];
                    arreglo[j + 1] = temp;
                }
            }
        }

        for (Object dato : arreglo) {
            ordenada.insertTail(dato);
        }

        return ordenada;
    }

    @Override
    public String toString() {
        if (cabeza == null) {
            return "[]";
        }

        String resultado = "[";
        DoubleNode actual = cabeza;

        while (actual != null) {
            resultado = resultado + actual.dato;
            if (actual.siguiente != null) {
                resultado = resultado + ", ";
            }
            actual = actual.siguiente;
        }
        resultado = resultado + "]";

        return resultado;
    }
}