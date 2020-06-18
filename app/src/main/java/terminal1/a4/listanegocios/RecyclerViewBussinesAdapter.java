package terminal1.a4.listanegocios;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import terminal1.a4.loginui.R;

public class RecyclerViewBussinesAdapter extends RecyclerView.Adapter<RecyclerViewBussinesAdapter.ViewHolder>{
    private Context context;
    private List<bussines_card> my_data;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(this.context).inflate(R.layout.bussines_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name, type, state, local, opinion;
        private ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name);
            type=(TextView)itemView.findViewById(R.id.type);
            state=(TextView)itemView.findViewById(R.id.state);
            local=(TextView)itemView.findViewById(R.id.local);
            //opinion=(TextView)itemView.findViewById(R.id.opinion);
            image=(ImageView) itemView.findViewById(R.id.image_neg);
        }

    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewBussinesAdapter.ViewHolder holder, int position) {
        holder.name.setText("Nombre: "+my_data.get(position).getNombre());
        holder.type.setText("Tipo: "+my_data.get(position).getTipo());
        holder.state.setText("Estado: "+my_data.get(position).getEstado());
        holder.local.setText("Local: "+my_data.get(position).getLocal());
        if (my_data.get(position).getTipo().equals("Restaurante")){

            holder.image.setBackgroundResource(R.drawable.rest_logo);
        }
        if (my_data.get(position).getTipo().equals("Tienda")){

            holder.image.setImageResource(R.drawable.tienda_logo);
        }

        if (my_data.get(position).getTipo().equals("Info")){

            holder.image.setImageResource(R.drawable.informacion_logo);
        }

        if (my_data.get(position).getTipo().equals("Vips")){

            holder.image.setImageResource(R.drawable.vip_logo);
        }





    }

    public RecyclerViewBussinesAdapter(List <bussines_card> tar, Context con){

        this.my_data=tar;
        this.context=con;
    }
}
