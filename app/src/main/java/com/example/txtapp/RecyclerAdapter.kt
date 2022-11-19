import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.txtapp.AddGroup
import com.example.txtapp.DailyJournal
import com.example.txtapp.R
import com.example.txtapp.RecyclerViewHolder

class RecyclerAdapter(    private val userList: ArrayList<RecyclerViewHolder>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    // create new views
    // Holds the views for adding it to image and text
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.tv_title)
        val itemDetail: TextView = itemView.findViewById(R.id.tv_description)
        val itemPicture: ImageView = itemView.findViewById(R.id.iv_Image)

        init {
            itemView.setOnClickListener{ v: View ->
                val position: Int = adapterPosition
                if(position == 0)
                {
                }
                Toast.makeText(itemView.context, "You clicekd on ${position+1}", Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_entry_journals, parent, false)
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = userList[position].groupName
        holder.itemDetail.text = userList[position].groupDetail
        holder.itemPicture.setImageResource(userList[position].groupImage)
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return userList.size
    }


}
