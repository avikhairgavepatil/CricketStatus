package avipatil.cricketstatus;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by avipatil on 3/18/2017.
 */

public class Cricket_Adapter extends ArrayAdapter {
    private Context context;
    private  int resource;
    private  List<Cricket_Pojo>cricket_pojolist;
    public Cricket_Adapter(Context context, int resource, List<Cricket_Pojo>cricket_pojolist) {
        super(context, resource, cricket_pojolist);
        this.context=context;
        this.resource=resource;
        this.cricket_pojolist=cricket_pojolist;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Cricket_Pojo cricket_pojo=cricket_pojolist.get(position);
        View view= LayoutInflater.from(context).inflate(resource,parent,false);
        TextView title= (TextView) view.findViewById(R.id.textView1);
        TextView id= (TextView) view.findViewById(R.id.textView2);
        TextView i= (TextView) view.findViewById(R.id.textView3);
        title.setText("Mataches"+":"+cricket_pojo.getDescription());
        id.setText("ID"+":"+cricket_pojo.getUnique_id());
        i.setText((CharSequence) cricket_pojo.getDate());
       // i.setText("date");

        return  view;
    }
}
