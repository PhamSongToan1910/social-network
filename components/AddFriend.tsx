import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity } from 'react-native';

interface AddFriendProps {
  userAvatar: string;
  userName: string;
  onAccept: () => void;
  onDecline: () => void;
}

const AddFriend: React.FC<AddFriendProps> = ({ userAvatar, userName, onAccept, onDecline }) => {
  return (
    <View style={styles.container}>
      <Image source={{ uri: userAvatar }} style={styles.avatar} />
      <View style={styles.infoContainer}>
        <Text style={styles.userName}>{userName}</Text>
        <Text style={styles.requestText}>đã gửi cho bạn lời mời kết bạn</Text>
      </View>
      <View style={styles.buttonContainer}>
        <TouchableOpacity style={[styles.button, styles.acceptButton]} onPress={onAccept}>
          <Text style={styles.buttonText}>Chấp nhận</Text>
        </TouchableOpacity>
        <TouchableOpacity style={[styles.button, styles.declineButton]} onPress={onDecline}>
          <Text style={[styles.buttonText, styles.declineText]}>Từ chối</Text>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'white',
    padding: 10,
    borderRadius: 8,
    marginBottom: 10,
  },
  avatar: {
    width: 50,
    height: 50,
    borderRadius: 25,
    marginRight: 10,
  },
  infoContainer: {
    flex: 1,
  },
  userName: {
    fontWeight: 'bold',
    fontSize: 16,
  },
  requestText: {
    color: '#65676B',
    fontSize: 14,
  },
  buttonContainer: {
    flexDirection: 'row',
  },
  button: {
    paddingHorizontal: 12,
    paddingVertical: 8,
    borderRadius: 6,
    marginLeft: 8,
  },
  acceptButton: {
    backgroundColor: '#1877F2',
  },
  declineButton: {
    backgroundColor: '#E4E6EB',
  },
  buttonText: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 14,
  },
  declineText: {
    color: '#050505',
  },
});

export default AddFriend;