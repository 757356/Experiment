package cn.itcast.experiment.MyData;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBank {
    public static final String DATA_FILE_NAME = "data";
    private final Context context;
    List<BookItem> bookItemList;

    public DataBank(Context context) {
        this.context=context;
    }

    @SuppressWarnings("unchecked")
    public List<BookItem> loadData() {
        bookItemList=new ArrayList<>();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(context.openFileInput(DATA_FILE_NAME));
            bookItemList = (ArrayList<BookItem>) objectInputStream.readObject();
            objectInputStream.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return bookItemList;
    }

    public void saveData() {
        ObjectOutputStream objectOutputStream=null;
        try{
            objectOutputStream = new ObjectOutputStream(context.openFileOutput(DATA_FILE_NAME, Context.MODE_PRIVATE));
            objectOutputStream.writeObject(bookItemList);
            objectOutputStream.close();
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
