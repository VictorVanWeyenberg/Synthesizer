package domein.interfaces;

import domein.interfaces.Observer;

public interface Subject<E> {

    public void addObserver(Observer observer);

    public void removeObserver(Observer observer);

    public void notifyObservers(E e);
}
