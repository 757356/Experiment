package cn.itcast.experiment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<BookItem> bookItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        RecyclerView mainRecycleView = findViewById( R.id.recycle_view_books );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        mainRecycleView.setLayoutManager( layoutManager );

        mainRecycleView.setAdapter( new MyRecyclerViewAdapter( bookItems ) );
    }

    public void initData(){
        bookItems=new ArrayList<BookItem>();
        bookItems.add( new BookItem( "信息安全数学基础 (第二版)",R.drawable.book_1 ) );
        bookItems.add( new BookItem("软件项目管理案例教程 (第四版)",R.drawable.book_2 ) );
        bookItems.add( new BookItem("创新工程实践",R.drawable.book_no_name ) );
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter {
        private List<BookItem> bookItems;

        public MyRecyclerViewAdapter(List<BookItem> bookItems) {
            this.bookItems = bookItems;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.book_item_holder, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder Holder, int position) {
            MyViewHolder holder = (MyViewHolder) Holder;

            holder.getImageView().setImageResource(bookItems.get(position).getCoverResourceId());
            holder.getTextViewName().setText(bookItems.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            return bookItems.size();
        }

        private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
            private final ImageView imageView;
            private final TextView textViewName;

            public MyViewHolder(View view) {
                super(view);

                this.imageView = view.findViewById(R.id.image_view_book_cover);
                this.textViewName = view.findViewById(R.id.text_view_book_title);

                itemView.setOnCreateContextMenuListener(this);
            }

            public ImageView getImageView() {
                return imageView;
            }

            public TextView getTextViewName() {
                return textViewName;
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                int position=getAdapterPosition();
                MenuItem menuItemAdd=contextMenu.add(Menu.NONE,1,1,"Add"+position);
                MenuItem menuItemDelete=contextMenu.add(Menu.NONE,2,2,"Delete"+position);

                menuItemAdd.setOnMenuItemClickListener(this);
                menuItemDelete.setOnMenuItemClickListener(this);
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int position= getAdapterPosition();
                switch(menuItem.getItemId())
                {
                    case 1:
                        View dialogueView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialogue_input_item,null);
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setView(dialogueView);

                        alertDialogBuilder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText editName = dialogueView.findViewById(R.id.edit_text_name);
                                bookItems.add( position, new BookItem( editName.getText().toString(),R.drawable.book_2 ) );
                                MyRecyclerViewAdapter.this.notifyItemInserted(position);
                            }
                        });
                        alertDialogBuilder.setCancelable(false).setNegativeButton ("取消",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alertDialogBuilder.create().show();;

                        break;
                    case 2:
                        bookItems.remove(position);
                        MyRecyclerViewAdapter.this.notifyItemRemoved(position);
                        break;
                }

                //Toast.makeText(MainActivity.this,"点击了"+menuItem.getItemId(), Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }
}