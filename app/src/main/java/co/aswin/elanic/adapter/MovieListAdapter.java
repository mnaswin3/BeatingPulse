package co.aswin.elanic.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import co.aswin.elanic.R;
import co.aswin.elanic.model.Movie;
import co.aswin.elanic.utils.ChangeUiUtils;
import co.aswin.elanic.utils.TypeUtils;

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    /**
     * Context
     */
    private Context context;

    /**
     * List for holding movie details {@link co.aswin.elanic.model.Movie}
     */
    private List<Movie> moviesList;

    /**
     * Determinig the view type
     */
    private int mViewType;

    public MovieListAdapter(Context context, List<Movie> moviesList, int viewType){
        this.context = context;
        this.moviesList = moviesList;
        this.mViewType = viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mViewType == TypeUtils.VIEW_TYPE_LINEAR){
            View itemView0 = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_normal, parent, false);
            return new LinearViewHolder(itemView0);
        }else if(mViewType == TypeUtils.VIEW_TYPE_GRID){
            View itemView1 = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_grid, parent, false);
            return new GridViewHolder(itemView1);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mViewType == TypeUtils.VIEW_TYPE_LINEAR){
            LinearViewHolder linearViewHolder
                    = (LinearViewHolder) holder;
            populateLinearView(linearViewHolder,position);
        }else if(mViewType == TypeUtils.VIEW_TYPE_GRID){
            GridViewHolder gridViewHolder
                    = (GridViewHolder) holder;
            populateGridView(gridViewHolder,position);
        }
    }

    @Override
    public int getItemCount() {
        if(moviesList != null && moviesList.size() > 0){
            return moviesList.size();
        }else {
            return 0;
        }
    }

    /**
     * Sets text into the view
     *
     * @param text text
     * @param view view
     */
    private void textValidator(String text, TextView view){
        if(text != null){
            view.setText(text);
        }
    }

    private void populateGridView(GridViewHolder holder, int position) {
        ChangeUiUtils.populateImageFromUrlToView(context,
                moviesList.get(position).getPosterPath(), holder.image);
        textValidator(moviesList.get(position).getTitle(),
                holder.title);
    }

    private void populateLinearView(LinearViewHolder holder, int position) {
        ChangeUiUtils.populateImageFromUrlToView(context,
                moviesList.get(position).getPosterPath(), holder.image);
        textValidator(moviesList.get(position).getTitle(),
                holder.title);
        textValidator(moviesList.get(position).getOverview(),
                holder.description);
    }

    /**
     * Adapter listener
     */
    private MovieListAdapter.OnAdapterInteractionListener listener;

    public void setListener(MovieListAdapter.OnAdapterInteractionListener listener) {
        this.listener = listener;
    }

    /**
     * Adapter  interface
     */
    public interface OnAdapterInteractionListener{
        void onMovieSelected(Movie movie, ImageView image, TextView title, TextView description);
        void onMovieSelected(Movie movie, ImageView image, TextView title);
        void onImageClicked(String imageUrl);
    }

    /**
     * View holder class for linear view
     */
    @SuppressWarnings("WeakerAccess")
    public class LinearViewHolder extends RecyclerView.ViewHolder {

        /**
         * Views in the custom view
         */
        @BindView(R.id.container) RelativeLayout container;
        @BindView(R.id.image) ImageView image;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.descriptioin) TextView description;

        private LinearViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onMovieSelected(moviesList.get(getAdapterPosition()),
                                image, title, description);
                    }
                }
            });

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onImageClicked(moviesList.get(getAdapterPosition()).getPosterPath());
                    }
                }
            });
        }
    }

    /**
     * View holder class for grid view
     */
    @SuppressWarnings("WeakerAccess")
    public class GridViewHolder extends RecyclerView.ViewHolder {

        /**
         * Views in the custom view
         */
        @BindView(R.id.container) RelativeLayout container;
        @BindView(R.id.image) ImageView image;
        @BindView(R.id.title) TextView title;

        private GridViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onMovieSelected(moviesList.get(getAdapterPosition()),
                                image, title);
                    }
                }
            });
        }
    }
}
