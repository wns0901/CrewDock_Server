package com.lec.spring.domains.portfolio.service;

import com.lec.spring.domains.portfolio.entity.Portfolio;
import com.lec.spring.domains.portfolio.entity.PortfolioStack;
import com.lec.spring.domains.portfolio.dto.PortfolioDto;
import com.lec.spring.domains.portfolio.dto.PortfolioStackDto;
import com.lec.spring.domains.portfolio.repository.PortfolioRepository;
import com.lec.spring.domains.project.entity.Project;
import com.lec.spring.domains.stack.entity.Stack;
import com.lec.spring.domains.user.entity.User;
import com.lec.spring.domains.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final PortfolioStackService portfolioStackService;
    private final UserRepository userRepository;

    @Override
    public List<PortfolioDto> getPortfoliosWithStacks(Long userId) {
        List<Portfolio> portfolios = portfolioRepository.findByUserIdWithStacksQueryDSL(userId);
        return portfolios.stream().map(PortfolioDto::new).collect(Collectors.toList());
    }

    @Override
    public List<PortfolioDto> getUserPortfoliosWithLimit(Long userId, int row) {
        List<Portfolio> portfolios = portfolioRepository.findByUserIdWithLimitQueryDSL(userId, row);
        return portfolios.stream().map(PortfolioDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PortfolioDto createPortfolio(PortfolioDto portfolioDto) {

        User user = userRepository.findById(portfolioDto.getUser().getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다: " + portfolioDto.getUser().getId()));


        Portfolio portfolio = Portfolio.builder()
                .title(portfolioDto.getTitle())
                .content(portfolioDto.getContent()!= null ? portfolioDto.getContent() : "")
                .user(user)
                .portfolioStack(new ArrayList<>())
                .build();

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        if (portfolioDto.getPortfolioStacks() != null && !portfolioDto.getPortfolioStacks().isEmpty()) {
            List<PortfolioStack> savedStacks = portfolioStackService.createPortfolioStacks(savedPortfolio, portfolioDto.getPortfolioStacks());
            savedPortfolio.getPortfolioStack().addAll(savedStacks);
        }

        return new PortfolioDto(savedPortfolio);
    }

    @Override
    @Transactional
    public PortfolioDto updatePortfolio(Long portfolioId, PortfolioDto portfolioDto) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("포트폴리오를 찾을 수 없음: " + portfolioId));

        portfolio.setTitle(portfolioDto.getTitle());
        portfolio.setContent(portfolioDto.getContent()!= null ? portfolioDto.getContent() : "");

        final Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        portfolioStackService.deleteByPortfolioId(savedPortfolio.getId());


        if (portfolioDto.getPortfolioStacks() != null && !portfolioDto.getPortfolioStacks().isEmpty()) {
            List<PortfolioStackDto> stackDtos = portfolioDto.getPortfolioStacks();
            List<PortfolioStack> updatedStacks = portfolioStackService.updatePortfolioStacks(savedPortfolio, stackDtos);
            savedPortfolio.getPortfolioStack().addAll(updatedStacks);
        }

        return new PortfolioDto(savedPortfolio);
    }



    @Override
    @Transactional
    public void deletePortfolio(Long portfolioId) {
        portfolioStackService.deleteByPortfolioId(portfolioId);
        portfolioRepository.deleteByPortfolioId(portfolioId);
    }

    @Override
    public void createPortfolioForProject(Project project, Long userId, List<Stack> stacks) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다: " + userId));


        Portfolio portfolio = Portfolio.builder()
                .title(project.getName())
                .content("프로젝트 자동 생성 포트폴리오")
                .user(user)
                .portfolioStack(new ArrayList<>())
                .build();

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);

        List<PortfolioStackDto> stackDtos = stacks.stream()
                .map(stack -> new PortfolioStackDto(null, portfolio.getId(), stack.getName()))
                .toList();

        if (!stackDtos.isEmpty()) {
            List<PortfolioStack> savedStacks = portfolioStackService.createPortfolioStacks(savedPortfolio, stackDtos);
            savedPortfolio.getPortfolioStack().addAll(savedStacks);
        }

        System.out.println("✅ 자동 생성된 포트폴리오 ID: " + savedPortfolio.getId());
    }

    // TODO
    @Override
    public PortfolioDto getPortfolioById(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("포트폴리오를 찾을 수 없음: " + portfolioId));

        return new PortfolioDto(portfolio);
    }
}