package terminal1.a4.loginui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAirAdapter extends RecyclerView.Adapter<RecyclerViewAirAdapter.ViewHolder> {
    private Context context;
    private ArrayList<air_card> my_data;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(this.context).inflate(R.layout.air_card,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView dst,da,aa;
        public ImageView qr;

        public ViewHolder(View itemView) {
            super(itemView);
            dst=itemView.findViewById(R.id.dst);
            da=itemView.findViewById(R.id.da);
            aa=itemView.findViewById(R.id.as);
            qr=itemView.findViewById(R.id.image_air);

        }

    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dst.setText("Destino: "+my_data.get(position).getDst());
        holder.aa.setText("Hora: "+my_data.get(position).getHora());
        holder.da.setText("Fecha: "+my_data.get(position).getDa());

    }
    public RecyclerViewAirAdapter (ArrayList<air_card> tar, Context con) {
        this.my_data = tar;
        this.context = con;
    }

}
