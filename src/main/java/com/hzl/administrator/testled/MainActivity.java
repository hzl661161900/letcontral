package com.hzl.administrator.testled;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    IntentFilter dtregeiste = new IntentFilter();
    Intent intent = new Intent();
    private Button button_red;
    private Button button_green;
    private Button button_blue;
    private Button button_allred;
    private Button button_allblue;
    private Button button_allgreen;
    private Button button_alloff;
    private Button button_update;
    private TextView textView_lednum;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    ListView listView;
    JSONArray jsonArray;
    private ListViewAdapter adapter;
    private List<Uled> beans = new ArrayList<Uled>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settings = getSharedPreferences("led", 0);
        editor = settings.edit();
        dtregeiste.addAction("com.hzl");
        registerReceiver(broadcastReceiver, dtregeiste);
        textView_lednum = (TextView) findViewById(R.id.textView_lednum);
        button_red = (Button) findViewById(R.id.button_red);
        button_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < beans.size(); i++) {
                    Uled l1 = beans.get(i);
                    if (l1.send) {
                        Log.e("server", "22222");
                        intent.setAction("com.xiazdong");
                        intent.putExtra("name", "red" + "," + l1.serID);
                        MainActivity.this.sendBroadcast(intent);
                    }
                }
//                intent.setAction("com.xiazdong");
//                intent.putExtra("name", "red");
//                MainActivity.this.sendBroadcast(intent);
            }
        });
        button_blue = (Button) findViewById(R.id.button_blue);
        button_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < beans.size(); i++) {
                    Uled l1 = beans.get(i);
                    if (l1.send) {
                        Log.e("server", "22222");
                        intent.setAction("com.xiazdong");
                        intent.putExtra("name", "blue" + "," + l1.serID);
                        MainActivity.this.sendBroadcast(intent);
                    }
                }
            }
        });
        button_green = (Button) findViewById(R.id.button_green);
        button_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < beans.size(); i++) {
                    Uled l1 = beans.get(i);
                    if (l1.send) {
                        Log.e("server", "22222");
                        intent.setAction("com.xiazdong");
                        intent.putExtra("name", "green" + "," + l1.serID);
                        MainActivity.this.sendBroadcast(intent);
                    }

                }
//                intent.setAction("com.xiazdong");
//                intent.putExtra("name", "green");
//                MainActivity.this.sendBroadcast(intent);
            }
        });
        button_allred = (Button) findViewById(R.id.button_allred);
        button_allred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setAction("com.xiazdong");
                intent.putExtra("name", "allred");
                MainActivity.this.sendBroadcast(intent);
            }
        });
        button_allblue = (Button) findViewById(R.id.button_allblue);
        button_allblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setAction("com.xiazdong");
                intent.putExtra("name", "allblue");
                MainActivity.this.sendBroadcast(intent);
                Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "1");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "audio/mp3");
                startActivity(intent);
            }
        });
        button_allgreen = (Button) findViewById(R.id.button_allgr);
        button_allgreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setAction("com.xiazdong");
                intent.putExtra("name", "allgreen");
                MainActivity.this.sendBroadcast(intent);

            }
        });
        button_alloff = (Button) findViewById(R.id.button_alloff);
        button_alloff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setAction("com.xiazdong");
                intent.putExtra("name", "alloff");
                MainActivity.this.sendBroadcast(intent);
            }
        });
        button_update = (Button) findViewById(R.id.button_updata);
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.setAction("com.xiazdong");
                intent.putExtra("name", "update");
                MainActivity.this.sendBroadcast(intent);
            }
        });
        listView = (ListView) findViewById(R.id.listView_led);
    }

    class light_br {
        String name;
        String key;
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        JSONArray jsonArray;

        @Override
        public void onReceive(Context context, Intent intent) {
            beans.clear();
            Log.e("server", intent.getExtras().getString("name"));
//            textView_lednum.setText("灯的数量为:"+jsonArray.length());
            try {
                JSONObject jsonObject = new JSONObject(intent.getExtras().getString("name"));
                jsonArray = jsonObject.getJSONArray("allsize");
                textView_lednum.setText("灯的数量为：" + jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonpet = jsonArray.getJSONObject(i);
                    if (jsonArray.length() != 0) {
                        editor.putString(jsonpet.getString("name"), jsonpet.getString("key"));
                        Uled u = new Uled();
                        u.name = jsonpet.getString("name");
                        u.serID = jsonpet.getString("key");
                        beans.add(u);
                        editor.commit();
                    }
                    Log.e("server", jsonpet.getString("key"));
                }

                adapter = new ListViewAdapter(MainActivity.this);
                listView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public class ListViewAdapter extends BaseAdapter {

        private Context context;

        class ViewHolder {
            TextView tvName;
            CheckBox cb;
        }

        public ListViewAdapter(Context context) {
            // TODO Auto-generated constructor stub
            this.context = context;
        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return beans.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return beans.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            // 页面
            final ViewHolder holder;
            final Uled bean = beans.get(position);
            LayoutInflater inflater = LayoutInflater.from(context);
            if (convertView == null) {
                convertView = inflater.inflate(
                        R.layout.ledselect_led, null);
                holder = new ViewHolder();
                holder.cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.tvName = (TextView) convertView
                        .findViewById(R.id.tv_device_name);
                convertView.setTag(holder);
            } else {
                // 取出holder
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvName.setText(bean.name);
            // 监听checkBox并根据原来的状态来设置新的状态
            holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Toast.makeText(MainActivity.this, holder.tvName.getText() + "选中了", Toast.LENGTH_LONG).show();
                        bean.send = true;
                    } else {
                        bean.send = false;
                    }
                }
            });

            return convertView;
        }


    }
}
