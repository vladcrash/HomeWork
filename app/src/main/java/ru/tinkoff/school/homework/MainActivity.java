package ru.tinkoff.school.homework;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mAdd;
    private RecyclerView mNodeRecyclerView;
    private NodeDao mNodeDao;
    private NodeNodeJoinDao mNodeNodeJoinDao;
    private NodeAdapter mNodeAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        mNodeDao = App.getNodeDatabase().getNodeDao();
        List<Node> nodes = mNodeDao.getAll();

        if (mNodeAdapter == null) {
            mNodeAdapter = new NodeAdapter(nodes);
            mNodeRecyclerView.setAdapter(mNodeAdapter);
        } else {
            mNodeAdapter.setNodes(nodes);
            mNodeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAdd = findViewById(R.id.add_edit_text);
        mNodeRecyclerView = findViewById(R.id.node_recycler_view);

        mNodeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mNodeNodeJoinDao = App.getNodeDatabase().getNodeNodeJoinDao();
        updateUI();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_button:
                if (mAdd.getText().toString().equals("")) {
                    return;
                }
                Node node = new Node(Integer.valueOf(mAdd.getText().toString()));
                long id = mNodeDao.insert(node);
                node.setId(id);
                mNodeAdapter.addNode(node);
                mNodeAdapter.notifyDataSetChanged();
                break;
        }
    }

    private int getColor(Node node) {
        List<Node> parents = mNodeNodeJoinDao.getParents(node.getId());
        List<Node> children = mNodeNodeJoinDao.getChildren(node.getId());

        if (!parents.isEmpty() && !children.isEmpty()) {
            return ContextCompat.getColor(MainActivity.this, R.color.red);
        } else if (!parents.isEmpty()) {
            return ContextCompat.getColor(MainActivity.this, R.color.blue);
        } else if (!children.isEmpty()) {
            return ContextCompat.getColor(MainActivity.this, R.color.yellow);
        } else {
            return ContextCompat.getColor(MainActivity.this, R.color.colorDefault);
        }
    }

    private class NodeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener{

        private TextView mNodeValue;
        private Node mNode;

        public NodeViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_node, parent, false));
            mNodeValue = itemView.findViewById(R.id.node_value);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bind(Node node) {
            mNode = node;
            mNodeValue.setText(String.valueOf(node.getValue()));
            mNodeValue.setBackgroundColor(getColor(node));

        }

        @Override
        public void onClick(View view) {
            RelationshipActivity.start(MainActivity.this, mNode.getId());
        }

        @Override
        public boolean onLongClick(View view) {
            removeThemAll(mNode);
            updateUI();
            mNodeAdapter.notifyDataSetChanged();
            return true;
        }
    }

    public void removeThemAll(Node node) {
        List<NodeNodeJoin> nodeNodeDown = mNodeNodeJoinDao.getChildrenJoin(node.getId());
        List<NodeNodeJoin> nodeNodeUp = mNodeNodeJoinDao.getParentsJoin(node.getId());
        if(!nodeNodeDown.isEmpty()) {
            for (NodeNodeJoin nodeNodeJoin : nodeNodeDown) {
                mNodeNodeJoinDao.delete(nodeNodeJoin);
                removeThemAll(mNodeDao.getItem(nodeNodeJoin.childId));
            }
        }

        if(!nodeNodeUp.isEmpty()) {
            for (NodeNodeJoin nodeNodeJoin : nodeNodeUp) {
                mNodeNodeJoinDao.delete(nodeNodeJoin);
            }
        }
        mNodeDao.delete(node);
        mNodeAdapter.deleteNode(node);
        mNodeAdapter.notifyDataSetChanged();
    }

    private class NodeAdapter extends RecyclerView.Adapter<NodeViewHolder> {
        List<Node> mNodes;

        public NodeAdapter(List<Node> nodes) {
            mNodes = nodes;
        }

        @Override
        public NodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new NodeViewHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(NodeViewHolder holder, int position) {
            Node node = mNodes.get(position);
            holder.bind(node);
        }


        @Override
        public int getItemCount() {
            return mNodes.size();
        }

        public void setNodes(List<Node> nodes) {
            mNodes = nodes;
        }

        public void addNode(Node node) {
            mNodes.add(node);
        }

        public void deleteNode(Node node) {
            mNodes.remove(node);
        }
    }


}
