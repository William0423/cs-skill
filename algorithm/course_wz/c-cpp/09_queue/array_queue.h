//
// Created by admin on 2019/9/9.
//

#ifndef C_CPP_ARRAY_QUEUE_H
#define C_CPP_ARRAY_QUEUE_H

template <typename T>

class ArrayQueue {
private:
    T* items_ = nullptr;
    size_t capacity_ = 0;
    size_t head_     = 0;
    size_t tail_     = 0;

public:
    ArrayQueue() = delete; // 这一句是什么意思？
    ArrayQueue(const size_t capacity) : capacity_(capacity) {
        items_ = new T[capacity_];
    }

    ~ArrayQueue() {
       if (nullptr != items_) {
           delete[] items_;
           items_ = nullptr;
       }
    }

    ArrayQueue(const ArrayQueue& other) : capacity_(other.capacity_) {
        items_ = new T[capacity_];
        for (size_t i = other.head_; i != other.tail_; ++i) {
            enqueue(other.items_[i]);
        }
    }

    ArrayQueue&operator=(const ArrayQueue& rhs) {
        delete[] items_;
        head_       = 0;
        tail_       = 0;
        capacity_   = rhs.capacity_;
        items_      = new T[capacity_];
        for (size_t i = rhs.head_; i != rhs.tail_; ++i) {
            enqueue(rhs.items_[i]);
        }
        return *this;
    }

    ArrayQueue(ArrayQueue&& other) : items_(other.items_),
                                     capacity_(other.capacity_),
                                     head_(other.head_),
                                     tail_(other.tail_) {
        other.items_    = nullptr;
        other.capacity_ = 0;
        other.head_     = 0;
        other.tail_     = 0;
    }

    ArrayQueue& operator=(ArrayQueue&& rhs) {
        delete[] items_;
        items_        = rhs.items_;
        capacity_     = rhs.capacity_;
        head_         = rhs.head_;
        tail_         = rhs.tail_;
        rhs.items_    = nullptr;
        rhs.capacity_ = 0;
        rhs.head_     = 0;
        rhs.tail_     = 0;
        return *this;
    }

public:
    void enqueue(T item) {
        if (capacity_ == tail_) {
            throw "Push data into a full queue!";
        }
        items_[tail_++] = item;
    }

    T head() const {
        if (head_ != tail_) {
            return items_[head_];
        } else {
            throw "Fetch data from an empty queue!";
        }
    }

    void dequeue() {
        if (head_ != tail_) {
            ++head_;
        } else {
            throw "Pop data from an empty queue!";
        }
    }

public:
    template <typename  UnaryFunc>
    void traverse(UnaryFunc do_traverse) {
        for (size_t i = head_; i != tail_; ++i) {
            do_traverse(items_[i]);
        }
    }
};

#endif //C_CPP_ARRAY_QUEUE_H
