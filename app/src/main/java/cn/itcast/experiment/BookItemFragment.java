package cn.itcast.experiment;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import cn.itcast.experiment.MyData.BookItem;
import cn.itcast.experiment.MyData.DataBank;

public class BookItemFragment extends Fragment {
    private List<BookItem> bookItems;

    private BookItemFragment.MyRecyclerViewAdapter recyclerViewAdapter;
    public static final int  RESULT_CODE_ADD_DATA = 1;

    private DataBank dataBank;
    ActivityResultLauncher<Intent> launcherAdd = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if(resultCode== RESULT_CODE_ADD_DATA){
                if(null==data)return;
                String name=data.getStringExtra("name");
                int position=data.getIntExtra("position",bookItems.size());
                bookItems.add(position,new BookItem(name,R.drawable.book_no_name));

                dataBank.saveData();

                recyclerViewAdapter.notifyItemInserted(position);

            }
        }
    });
    ActivityResultLauncher<Intent> launcherEdit = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            int resultCode = result.getResultCode();
            if(resultCode == RESULT_CODE_ADD_DATA){
                if(null==data)
                    return;
                String name=data.getStringExtra("name");
                int position=data.getIntExtra("position",bookItems.size());
                bookItems.get(position).setName( name );

                dataBank.saveData();

                recyclerViewAdapter.notifyItemChanged(position);

            }
        }
    });

    public BookItemFragment() {

    }

    public static BookItemFragment newInstance( BookItemFragment fragment ) {
//        BookItemFragment fragment = new BookItemFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_book_item, container, false);

        initData();
        RecyclerView mainRecycleView = rootView.findViewById( R.id.recycle_view_items );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this.getContext() );
        mainRecycleView.setLayoutManager( layoutManager );

        //mainRecycleView.setAdapter( new MyRecyclerViewAdapter( bookItems ) );

        recyclerViewAdapter = new BookItemFragment.MyRecyclerViewAdapter(bookItems);
        mainRecycleView.setAdapter(recyclerViewAdapter);

        return rootView;
    }

    public void initData(){
        dataBank = new DataBank( this.getContext() );
        bookItems = dataBank.loadData();

//        bookItems=new ArrayList<BookItem>();
//        bookItems.add( new BookItem( "信息安全数学基础 (第二版)",R.drawable.book_1 ) );
//        bookItems.add( new BookItem("软件项目管理案例教程 (第四版)",R.drawable.book_2 ) );
//        bookItems.add( new BookItem("创新工程实践",R.drawable.book_no_name ) );
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<BookItemFragment.MyRecyclerViewAdapter.MyViewHolder> {
        private List<BookItem> bookItems;

        public MyRecyclerViewAdapter(List<BookItem> bookItems) {
            this.bookItems = bookItems;
        }

        @NonNull
        @Override
        public BookItemFragment.MyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.book_item_holder, parent, false);

            return new BookItemFragment.MyRecyclerViewAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BookItemFragment.MyRecyclerViewAdapter.MyViewHolder Holder, int position) {
//            MyViewHolder holder = (MyViewHolder) Holder;

            Holder.getImageView().setImageResource(bookItems.get(position).getCoverResourceId());
            Holder.getTextViewName().setText(bookItems.get(position).getTitle());
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
                MenuItem menuItemEdit=contextMenu.add(Menu.NONE, 3, 3, "Edit"+position);

                menuItemAdd.setOnMenuItemClickListener(this);
                menuItemDelete.setOnMenuItemClickListener(this);
                menuItemEdit.setOnMenuItemClickListener(this);
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int position= getAdapterPosition();
                Intent intent;
                switch(menuItem.getItemId())
                {
                    case 1:
//                        View dialogueView = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialogue_input_item,null);
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
//                        alertDialogBuilder.setView(dialogueView);
//
//                        alertDialogBuilder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                EditText editName = dialogueView.findViewById(R.id.edit_text_name);
//                                bookItems.add( position, new BookItem( editName.getText().toString(),R.drawable.book_2 ) );
//                                MyRecyclerViewAdapter.this.notifyItemInserted(position);
//                            }
//                        });
//                        alertDialogBuilder.setCancelable(false).setNegativeButton ("取消",new DialogInterface.OnClickListener(){
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                            }
//                        });
//                        alertDialogBuilder.create().show();;
                        intent=new Intent(BookItemFragment.this.getContext(),EditBookActivity.class);
                        intent.putExtra("position",position);
                        launcherAdd.launch(intent);
                        break;
                    case 2:// there may be having bugs
                        bookItems.remove(position);
                        dataBank.saveData();
                        MyRecyclerViewAdapter.this.notifyItemRemoved(position);
                        break;
                    case 3:
                        intent=new Intent(BookItemFragment.this.getContext(),EditBookActivity.class);
                        intent.putExtra("position",position);
                        intent.putExtra("name",bookItems.get(position).getTitle());
                        launcherEdit.launch(intent);
                        break;
                }

                //Toast.makeText(MainActivity.this,"点击了"+menuItem.getItemId(), Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }
}
