package ru.tinkoff.school.homework;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Влад on 13.11.2017.
 */

public class RelationshipFragment extends Fragment {

    private static final String ITEM_ID = "ITEM_ID";
    private static final String ITEM_ROLE = "ITEM_ROLE";

    private RecyclerView mNodeRecyclerView;
    private NodeAdapter mNodeAdapter;
    private long mId;
    private boolean mRole;
    private NodeNodeJoinDao mNodeNodeJoinDao;
    private NodeDao mNodeDao;
    private List<Node> mNodes;
    private EditText mAdd;
    private Button mAddButton;

    public static RelationshipFragment newInstance(long id, boolean parent) {
        Bundle args = new Bundle();
        args.putLong(ITEM_ID, id);
        args.putBoolean(ITEM_ROLE, parent);

        RelationshipFragment fragment = new RelationshipFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mId = getArguments().getLong(ITEM_ID);
        mRole = getArguments().getBoolean(ITEM_ROLE);

        mNodeNodeJoinDao = App.getNodeDatabase().getNodeNodeJoinDao();
        mNodeDao = App.getNodeDatabase().getNodeDao();

        if(mRole) {
            mNodes = mNodeNodeJoinDao.getParents(mId);
        } else {
            mNodes = mNodeNodeJoinDao.getChildren(mId);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_relationship, container, false);

        mAdd = view.findViewById(R.id.add_edit_text);
        mAddButton = view.findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAdd.getText().toString().equals("")) {
                    return;
                }
                Node node = new Node(Integer.valueOf(mAdd.getText().toString()));
                long id = mNodeDao.insert(node);
                node.setId(id);
                NodeNodeJoin nodeNodeJoin;
                if (mRole) {
                    nodeNodeJoin = new NodeNodeJoin(id, mId);
                } else {
                    nodeNodeJoin = new NodeNodeJoin(mId ,id);
                }
                mNodeNodeJoinDao.insert(nodeNodeJoin);
                mNodeAdapter.addNode(node);
                mNodeAdapter.notifyDataSetChanged();
            }
        });

        mNodeRecyclerView = view.findViewById(R.id.node_recycler_view);
        mNodeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNodeAdapter = new NodeAdapter(mNodes);
        mNodeRecyclerView.setAdapter(mNodeAdapter);
        return view;
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
                removeThemAll(mNodeDao.getItem(nodeNodeJoin.parentId));
            }
        }
        mNodeDao.delete(node);

    }


    private class NodeViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        private TextView mNodeValue;
        private Node mNode;

        public NodeViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_node, parent, false));
            mNodeValue = itemView.findViewById(R.id.node_value);
            itemView.setOnLongClickListener(this);
        }

        public void bind(Node node) {
            mNode = node;
            mNodeValue.setText(String.valueOf(node.getValue()));
        }

        @Override
        public boolean onLongClick(View view) {
            removeThemAll(mNode);
            mNodeAdapter.deleteNode(mNode);
            mNodeAdapter.notifyDataSetChanged();
            return true;
        }
    }

    private class NodeAdapter extends RecyclerView.Adapter<NodeViewHolder> {

        private List<Node> mNodes;

        public NodeAdapter(List<Node> nodes) {
            mNodes = nodes;
        }

        @Override
        public NodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
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

        public void addNode(Node node) {
            mNodes.add(node);
        }

        public void deleteNode(Node node) {
            mNodes.remove(node);
        }
    }
}

