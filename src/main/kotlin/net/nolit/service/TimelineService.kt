package net.nolit.dredear.service

import net.nolit.dredear.entity.Timeline
import net.nolit.dredear.entity.TimelineType
import net.nolit.dredear.entity.User
import net.nolit.dredear.repository.TaskRepository
import net.nolit.dredear.repository.TimelineRepository
import net.nolit.dredear.repository.UserRepository
import net.nolit.repository.TimelineTypeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TimelineService (
        private val repository: TimelineRepository,
        private val typeRepository: TimelineTypeRepository,
        private val userRepository: UserRepository,
        private val taskRepository: TaskRepository
) {
    @Transactional(readOnly = true)
    fun getTimelineEveryUser(followingUserId: Int): List<User> {
        val followingUser = userRepository.findByIdOrNull(followingUserId)
        if (followingUser === null) {
            throw NullPointerException()
        }
        val userList =  userRepository.getFollowedUser(followingUser.id)
        val filteredUserList = userList.filter{user -> ! user.timelines.isEmpty()}
        val mutableUserList = filteredUserList.toMutableList()
        mutableUserList.add(followingUser)
        for (user in mutableUserList) {
            user.tasks = listOf()
        }
        return mutableUserList
    }

    @Transactional
    fun addAchievedTimeline(userId: Int, taskId: Int) {
        val timeline = Timeline()
        timeline.userId = userId
        timeline.task = taskRepository.findByIdOrNull(taskId)
        timeline.type = typeRepository.findByIdOrNull(TimelineType.ACHIEVED)
        repository.save(timeline)
    }

    @Transactional
    fun addDeclaredTimeline(userId: Int, taskId: Int) {
        val timeline = Timeline()
        timeline.userId = userId
        timeline.task = taskRepository.findByIdOrNull(taskId)
        timeline.type = typeRepository.findByIdOrNull(TimelineType.DECLARED)
        repository.save(timeline)
    }
}