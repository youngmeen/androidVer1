package com.example.kim.finalprojecttrack1.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kim.finalprojecttrack1.R;

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {

        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home, container, false);

        String[] menuItem={"Do something",
                "Do something else!",
                "Do yet another thing!"};
        ListView listView= (ListView)view.findViewById(R.id.mainMenu);
        ArrayAdapter<String> listViewAdapter=new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menuItem
        );

        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener((adapterView, view1, position, id) -> {
            if(position==0){
                Toast.makeText(getActivity()," 너가 체크하다 첫번쩨 아이템을",Toast.LENGTH_SHORT).show();
            }else if(position==1){
                Toast.makeText(getActivity()," 너가 체크하다 두번쩨 아이템을",Toast.LENGTH_SHORT).show();
            }else if(position==2){
                Toast.makeText(getActivity()," 너가 체크하다 세번쩨 아이템을",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
