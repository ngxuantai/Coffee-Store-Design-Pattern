package com.example.coffestoreapp.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.coffestoreapp.Command.CommandInvoker;
import com.example.coffestoreapp.Command.DeleteDrinkCommand;

import com.example.coffestoreapp.Activities.AddMenuActivity;
import com.example.coffestoreapp.Activities.AmountMenuActivity;
import com.example.coffestoreapp.Activities.HomeActivity;
import com.example.coffestoreapp.CustomAdapter.AdapterDisplayMenu;
import com.example.coffestoreapp.DAO.DrinkDAO;
import com.example.coffestoreapp.DTO.DrinkDTO;
import com.example.coffestoreapp.R;

import java.util.List;

public class DisplayMenuFragment extends Fragment {
    int categoryId, tableId;
    String categoryName, status;
    GridView gvDisplayMenu;
    DrinkDAO drinkDAO;
    List<DrinkDTO> drinkDTOList;
    AdapterDisplayMenu adapterDisplayMenu;
    private CommandInvoker invoker;

    ActivityResultLauncher<Intent> resultLauncherMenu = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        boolean check = intent.getBooleanExtra("check", false);
                        String function = intent.getStringExtra("function");
                        if (function.equals("addDrink")) {
                            if (check) {
                                ShowDrinkList();
                                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (check) {
                                ShowDrinkList();
                                Toast.makeText(getActivity(), "Sửa thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Sửa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.displaymenu_layout, container, false);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("Quản lý thực đơn");
        drinkDAO = DrinkDAO.getInstance(getActivity());
        invoker = new CommandInvoker();

        Button btnUndo = view.findViewById(R.id.btnUndo);
        btnUndo.setVisibility(View.GONE); // Ẩn nút Undo ban đầu
        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invoker.undoLastCommand();
                ShowDrinkList();
                Toast.makeText(getActivity(), "Undo thành công", Toast.LENGTH_SHORT).show();
                if (!invoker.hasCommands()) {
                    btnUndo.setVisibility(View.GONE);
                }
            }
        });

        gvDisplayMenu = (GridView) view.findViewById(R.id.gvDisplayMenu);

        Bundle bundle = getArguments();
        if (bundle != null) {
            categoryId = bundle.getInt("categoryId");
            categoryName = bundle.getString("categoryName");
            tableId = bundle.getInt("tableId");
            ShowDrinkList();

            gvDisplayMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // nếu lấy đc mã bàn mới mở
                    status = drinkDTOList.get(position).getStatus();
                    if (tableId != 0) {
                        if (status.equals("true")) {
                            Intent iAmount = new Intent(getActivity(), AmountMenuActivity.class);
                            iAmount.putExtra("tableId", tableId);
                            iAmount.putExtra("drinkId", drinkDTOList.get(position).getDrinkID());
                            startActivity(iAmount);
                        } else {
                            Toast.makeText(getActivity(), "Món đã hết, không thể thêm", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        setHasOptionsMenu(true);
        registerForContextMenu(gvDisplayMenu);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    getParentFragmentManager().popBackStack("showCategory", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });

        return view;
    }

    // tạo 1 menu context show lựa chọn
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
    }

    // Tạo phần sửa và xóa trong menu context
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = menuInfo.position;
        int drinkID = drinkDTOList.get(position).getDrinkID();
        invoker = new CommandInvoker();

        switch (id) {
            case R.id.itEdit:
                Intent iEdit = new Intent(getActivity(), AddMenuActivity.class);
                iEdit.putExtra("drinkId", drinkID);
                iEdit.putExtra("categoryId", categoryId);
                iEdit.putExtra("categoryName", categoryName);
                resultLauncherMenu.launch(iEdit);
                break;

            case R.id.itDelete:
                DeleteDrinkCommand deleteDrinkCommand = new DeleteDrinkCommand(drinkID);
                deleteDrinkCommand.setDrinkDAO(drinkDAO);
                boolean check = invoker.executeCommand(deleteDrinkCommand);

                //boolean check = drinkDAO.deleteDrink(drinkID);
                
                if (check) {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.delete_sucessful),
                        Toast.LENGTH_SHORT).show();
                    ShowDrinkList();
                    Button btnUndo = getView().findViewById(R.id.btnUndo);
                    btnUndo.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.delete_failed),
                            Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        MenuItem itAddMenu = menu.add(1, R.id.itAddMenu, 1, R.string.addMenu);
        itAddMenu.setIcon(R.drawable.ic_baseline_add_24);
        itAddMenu.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itAddMenu:
                Intent intent = new Intent(getActivity(), AddMenuActivity.class);
                intent.putExtra("categoryId", categoryId);
                intent.putExtra("categoryName", categoryName);
                resultLauncherMenu.launch(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ShowDrinkList() {
        drinkDTOList = drinkDAO.getListDrinkByCategoryId(categoryId);
        adapterDisplayMenu = new AdapterDisplayMenu(getActivity(), R.layout.custom_layout_displaymenu, drinkDTOList);
        gvDisplayMenu.setAdapter(adapterDisplayMenu);
        adapterDisplayMenu.notifyDataSetChanged();
    }
}
