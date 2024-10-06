import React, { useState } from 'react';
import { View, Text, TouchableOpacity, StyleSheet, Modal, TextInput, FlatList } from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import LikeButton from './LikeButton';

interface Comment {
  id: string;
  username: string;
  text: string;
  likes: number;
  createdAt: string; // Thêm trường này
}

interface CommentButtonProps {
  postId: string;
  commentCount: number;
}

const CommentButton: React.FC<CommentButtonProps> = ({ postId, commentCount }) => {
  const [modalVisible, setModalVisible] = useState(false);
  const [newComment, setNewComment] = useState('');
  const [comments, setComments] = useState<Comment[]>([]);

  const handleOpenComments = () => {
    // Tại đây, bạn có thể gọi API để lấy danh sách comments
    // Ví dụ:
    // fetchComments(postId).then(setComments);
    setModalVisible(true);
  };

  const handleAddComment = () => {
    if (newComment.trim()) {
      const comment: Comment = {
        id: Date.now().toString(),
        username: 'CurrentUser', // Thay thế bằng username thực tế
        text: newComment,
        likes: 0,
        createdAt: new Date().toISOString(), // Thêm thời gian tạo comment
      };
      setComments([...comments, comment]);
      setNewComment('');
      // Tại đây, bạn có thể gọi API để lưu comment
      // Ví dụ:
      // saveComment(postId, comment);
    }
  };

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return `${date.getDate()}/${date.getMonth() + 1}`; // Ngày/Tháng
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
  button: {
    flexDirection: 'row',
    alignItems: 'center',
    marginTop: 15,
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
    // marginTop: 5,
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