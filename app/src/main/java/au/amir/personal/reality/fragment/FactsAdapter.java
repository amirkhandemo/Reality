package au.amir.personal.reality.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import au.amir.personal.reality.R;
import au.amir.personal.reality.model.FactsSheet;
import au.amir.personal.reality.service.MyService;



public class FactsAdapter extends RecyclerView.Adapter<FactsAdapter.factsViewHolder> {

    private LayoutInflater inflater;
    Context context;

    public FactsAdapter(Context context) {
        inflater = LayoutInflater.from(context);

        this.context=context;
    }

    @Override
    public factsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.fact_view,viewGroup,false);
        factsViewHolder holder = new factsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(factsViewHolder viewHolder, int i) {
        FactsSheet.Row factRow= MyService.getInstance()
                .getFactsSheet().getRows().get(i);
        viewHolder.title.setText(factRow.getTitle());
        viewHolder.description.setText(factRow.getDescription());
        viewHolder.factsImage.setImageResource(R.drawable.blankicon);  // to avoid recycle view image

        if (factRow.getImageHref()!=null && factRow.getImageHref().length()!=0)
            Picasso.with(context).load(factRow.getImageHref()).into(viewHolder.factsImage);
    }

    @Override
    public int getItemCount() {
        return MyService.getInstance().getFactsSheet().getRows().size();
    }

    class factsViewHolder extends  RecyclerView.ViewHolder{
        TextView title;
        TextView description;
        ImageView factsImage;

        factsViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            factsImage = (ImageView) itemView.findViewById(R.id.facts_image);
        }

    }
}
