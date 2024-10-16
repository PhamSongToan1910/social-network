import React, { useState, useEffect } from 'react';
import { View, Text, TouchableOpacity, StyleSheet, Modal, TextInput, FlatList } from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import LikeButton from './LikeButton';
import { Post } from '../types/types';
import { Client } from '@stomp/stompjs'; // Import STOMP client
import Stomp from "stompjs";
import SockJS from "sockjs-client";

interface Comment {
  id: string;
  username: string;
  text: string;
  likes: number;
  createdAt: string;
  postID: Post['postID'];
}

interface CommentButtonProps {
  postID: Post['postID'];
  commentCount: number;
}

const CommentButton: React.FC<CommentButtonProps> = ({ postID, commentCount }) => {
  const [modalVisible, setModalVisible] = useState(false);
  const [newComment, setNewComment] = useState('');
  const [comments, setComments] = useState<Comment[]>([]);
  const [client, setClient] = useState<Client | null>(null); // STOMP client

  useEffect(() => {
    // Initialize the STOMP client with SockJS
    const socket = new SockJS('http://YOUR_SERVER_URL/ws'); // Replace with your WebSocket URL
    const stompClient = new Client({
      webSocketFactory: () => socket, // Use SockJS for WebSocket connection
      reconnectDelay: 5000, // Auto reconnect after disconnect
      onConnect: () => {
        console.log('Connected to WebSocket via STOMP');
        
        // Subscribe to the comments topic for the specific post
        stompClient.subscribe(`/topic/comments/${postID}`, (message) => {
          const comment = JSON.parse(message.body);
          if (comment.postID === postID) {
            setComments((prevComments) => [...prevComments, comment]);
          }
        });
      },
      onWebSocketError: (error) => {
        console.error('WebSocket error:', error);
      },
    });
  
    stompClient.activate(); // Activate STOMP client
    setClient(stompClient);
  
    return () => {
      stompClient.deactivate(); // Deactivate STOMP when component unmounts
    };
  }, [postID]);
  

  const handleOpenComments = async () => {
    try {
      const response = await fetch(`https://66f8c7962a683ce9731022f3.mockapi.io/user/${postID}`);
      if (!response.ok) {
        throw new Error('Failed to fetch post data');
      }
      const postData = await response.json();
      console.log('Post data:', postData);

      await fetchComments(postID);
      setModalVisible(true);
    } catch (error) {
      console.error('Error fetching post data:', error);
    }
  };

  const fetchComments = async (postID: string) => {
    try {
      const response = await fetch(`https://66f8c7962a683ce9731022f3.mockapi.io/user/?postID=${postID}`);
      const data = await response.json();
      setComments(data);
    } catch (error) {
      console.error('Error fetching comments:', error);
    }
  };

  const handleAddComment = () => {
    if (newComment.trim() && client && client.connected) {
      const comment: Comment = {
        id: Date.now().toString(),
        username: 'CurrentUser', 
        text: newComment,
        likes: 0,
        createdAt: new Date().toISOString(),
        postID: postID,
      };
  
      client.publish({
        destination: `/app/comments/${postID}`, // Destination to send the message
        body: JSON.stringify(comment),          // Message body (stringified comment)
      });
      
  
      setNewComment(''); // Clear the comment input
    }
  };
  

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return `${date.getDate()}/${date.getMonth() + 1}`;
  };

  const renderComment = ({ item }: { item: Comment }) => (
    <View style={styles.commentItem}>
      <View style={styles.commentContent}>
        <Text style={styles.commentUsername}>{item.username}</Text>
        <Text>{item.text}</Text>
      </View>
      <View style={styles.commentActions}>
        <View style={styles.likeReplyContainer}>
          <View ><Text style={styles.commentTime}>{formatDate(item.createdAt)}</Text></View>
          <LikeButton />
          <TouchableOpacity style={styles.replyButton}>
            <Text style={styles.replyButtonText}>Trả lời</Text>
          </TouchableOpacity>
        </View>
      </View>
    </View>
  );

  return (
    <View>
      {/* Display comment count */}
      <View style={styles.commentCountContainer}>
        <Text style={styles.commentCountText}>{commentCount} Bình luận</Text>
      </View>

      <TouchableOpacity style={styles.button} onPress={handleOpenComments}>
        <Icon name="comment-o" size={24} color="#333" />
        <Text style={styles.buttonText}>Comment {commentCount}</Text>
      </TouchableOpacity>

      <Modal
        animationType="slide"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => setModalVisible(false)}
      >
        <View style={styles.modalContainer}>
          <View style={styles.modalContent}>
            <TouchableOpacity
              style={styles.closeButton}
              onPress={() => setModalVisible(false)}
            >
              <Icon name="close" size={24} color="#333" />
            </TouchableOpacity>
            <FlatList
              data={comments}
              renderItem={renderComment}
              keyExtractor={(item) => item.id}
              style={styles.commentList}
            />
            <View style={styles.inputContainer}>
              <TextInput
                style={styles.input}
                value={newComment}
                onChangeText={setNewComment}
                placeholder="Thêm bình luận..."
              />
              <TouchableOpacity style={styles.sendButton} onPress={handleAddComment}>
                <Icon name="send" size={24} color="#007AFF" />
              </TouchableOpacity>
            </View>
          </View>
        </View>
      </Modal>
    </View>
  );
};

const styles = StyleSheet.create({
  commentCountContainer: {
    marginTop: 10,
    alignItems: 'center',
  },
  commentCountText: {
    fontSize: 12,
    color: '#666',
  },
  button: {
    flexDirection: 'row',
    alignItems: 'center',
    marginTop: 5,
  },
  buttonText: {
    marginLeft: 8,
    fontSize: 14,
    color: '#333',
  },
  modalContainer: {
    flex: 1,
    justifyContent: 'flex-end',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  modalContent: {
    backgroundColor: 'white',
    borderTopLeftRadius: 20,
    borderTopRightRadius: 20,
    paddingTop: 20,
    paddingHorizontal: 20,
    paddingBottom: 40,
    maxHeight: '80%',
  },
  closeButton: {
    alignSelf: 'flex-end',
    padding: 10,
  },
  commentList: {
    marginBottom: 10,
  },
  commentItem: {
    paddingVertical: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#eee',
  },
  commentContent: {
    marginBottom: 5,
  },
  commentUsername: {
    fontWeight: 'bold',
    marginBottom: 4,
  },
  commentActions: {
    flexDirection: 'row',
    justifyContent: 'flex-start',
    alignItems: 'center',
  },
  likeReplyContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  commentTime: {
    fontSize: 12,
    color: '#888',
    marginRight: 10,
  },
  replyButton: {
    marginLeft: 15,
  },
  replyButtonText: {
    color: '#888',
    fontSize: 12,
  },
  inputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    borderTopWidth: 1,
    borderTopColor: '#eee',
    paddingTop: 10,
  },
  input: {
    flex: 1,
    borderWidth: 1,
    borderColor: '#ddd',
    borderRadius: 20,
    paddingHorizontal: 15,
    paddingVertical: 8,
    marginRight: 10,
  },
  sendButton: {
    padding: 5,
  },
});

export default CommentButton;
