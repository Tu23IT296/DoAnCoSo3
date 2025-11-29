package com.example.taskmaster.service;

import com.example.taskmaster.dto.TagDTO;
import com.example.taskmaster.model.Tag;
import com.example.taskmaster.model.User;
import com.example.taskmaster.repository.TagRepository;
import com.example.taskmaster.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final UserService userService;

    public List<Tag> getAllTags() {
        User currentUser = userService.getCurrentUser();
        return tagRepository.findByUser(currentUser);
    }

    public Optional<Tag> getTagById(Integer id) {
        User currentUser = userService.getCurrentUser();
        Optional<Tag> tag = tagRepository.findById(id);
        return tag.filter(t -> t.getUser().getUid().equals(currentUser.getUid()));
    }

    public Tag createTag(TagDTO tagDTO) {
        User currentUser = userService.getCurrentUser();

        Tag tag = new Tag();
        tag.setUser(currentUser);
        tag.setName(tagDTO.getName());
        tag.setColor(tagDTO.getColor());

        return tagRepository.save(tag);
    }

    public void deleteTag(Integer id) {
        User currentUser = userService.getCurrentUser();
        Optional<Tag> tag = tagRepository.findById(id);
        if (tag.isPresent() && tag.get().getUser().getUid().equals(currentUser.getUid())) {
            tagRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Tag not found or not authorized");
        }
    }
}