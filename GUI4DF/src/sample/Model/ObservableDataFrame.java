package sample.Model;

import DF.DataFrame;
import DF.Values.Value;
import javafx.collections.ObservableListBase;

public class ObservableDataFrame extends ObservableListBase<Value[]> {

    private DataFrame container;

    public ObservableDataFrame (DataFrame df){
        container=df;
    }

    @Override
    public Value[] get(int index) {return container.zwrocWiersz(index);}

    @Override
    public int size() {return container.size();}



}
